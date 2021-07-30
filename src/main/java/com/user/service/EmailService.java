package com.user.service;

import com.user.model.entities.Email;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.EmailPreferencesEnum;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.PrivacyEnum;
import com.user.model.entities.enums.SecurityLogEnum;
import com.user.model.repositories.EmailRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.EmailValidator;
import com.user.validator.UpdateEmailBackupValidator;
import com.user.validator.UpdateEmailPreferencesValidator;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class EmailService extends AbstractService<Email, EmailRepository> {

    @Override
    public Page<Email> getAllBy(Pageable pageable, String searchBy, String searchValue) {
        switch (searchBy) {
            case "email":
                return this.getRepository().findByEmailContaining(searchValue, pageable);
            case "priority":
                return this.getRepository().findByPriorityContaining(PriorityEnum.valueOf(searchValue), pageable);
            case "privacy":
                return this.getRepository().findByPrivacyContaining(PrivacyEnum.valueOf(searchValue), pageable);
            case "null":
                super.getAllBy(pageable, searchBy, searchValue);
            default:
                this.responseStatus(HttpStatus.BAD_REQUEST, "By " + searchBy + " is incorrect");
        }
        return null;
    }

    @Override
    public void create(AbstractValidator abstractValidator) {
        EmailValidator validator = (EmailValidator) abstractValidator;
        UserSecurity u = this.getUser();
        Email e = this.create(validator.getEmail().toLowerCase(), PriorityEnum.SECONDARY);
        u.addEmail(e);
        userSecurityRepository.save(u);
        securityLogService.create(SecurityLogEnum.EMAIL_ADDED, u, "Email " + e.getEmail() + " added");
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }

    public Email create(String email, PriorityEnum pe) {
        if (this.getRepository().existsByEmail(email)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This email is already in the database");
        }
        Email e = emailFacade.newInstance(email, pe, PrivacyEnum.PRIVATE);
        emailFacade.initToken(e);
        this.getRepository().save(e);
        return e;
    }

    @Override
    public void delete(long id) {
        if (!this.getRepository().existsById(id)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        }
        Email e = this.getRepository().findById(id).get();
        if (e.getPriority() == PriorityEnum.PRIMARY) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "Cannot delete PRINCIPAL email");
        } else {
            UserSecurity u = userSecurityRepository.findByEmailId(id).get();
            u.getEmailList().remove(e);
            userSecurityRepository.save(u);
            this.getRepository().deleteById(id);
            securityLogService.create(SecurityLogEnum.EMAIL_REMOVED, u, "Email " + e.getEmail() + " removed");
            this.responseStatus(HttpStatus.NO_CONTENT, "Email deleted");
        }
    }

    public void resendConfirmEmail(long id) {
        if (!userSecurityRepository.existsByEmailId(id)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The email is not correct");
        }
        if (!this.getRepository().existsById(id)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        }
        Email e = this.getRepository().findById(id).get();
        UserSecurity u = this.getUser();
        boolean contain = false;
        for (Email ee : u.getEmailList()) {if (ee.getId() == id) {contain = true; break;}}
        if (!contain) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The user don't have this email");
        }
        emailFacade.initToken(e);
        this.getRepository().save(e);
    }

    public void confirmEmail(String token) {
        if (!this.getRepository().existsByEmailConfirmedToken(token)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This token is doesn't exist");
        }
        Email e = this.getRepository().findByEmailConfirmedToken(token).get();
        emailFacade.confirmToken(e);
        this.getRepository().save(e);
    }

    public void updatePriority(EmailValidator validator) {
        if (!this.getRepository().existsByEmail(validator.getEmail())) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The email is not correct");
        }
        UserSecurity u = this.getUser();
        if (!u.hasEmail(validator.getEmail())) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The user don't have this email");
        }
        if (!u.getPrincipalEmail().getEmailConfirmed()) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The email is not confirmed");
        }
        for (Email e : u.getEmailList()) {
            if (e.getPriority() == PriorityEnum.PRIMARY) {
                e.setPriority(PriorityEnum.SECONDARY);
                emailRepository.save(e);
            }
        }
        Email e = this.getRepository().findByEmail(validator.getEmail()).get();
        e.setPriority(PriorityEnum.PRIMARY);
        e.setBackup(false);
        this.getRepository().save(e);
        userSecurityRepository.save(u);
        securityLogService.create(SecurityLogEnum.EMAIL_CHANGE_PRIORITY, u, "Email " + validator.getEmail() + " updated to Principal");
        this.responseStatus(HttpStatus.NO_CONTENT, "Success new email added");
    }

    public void updateBackup(long id, UpdateEmailBackupValidator validator) {
        UserSecurity u = this.getUser();
        boolean contain = false;
        Email e = null;
        for (Email ee : u.getEmailList()) {if (ee.getId() == id) {contain = true; e = ee; break;}}
        if (!contain) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The user don't have this email");
        }
        e.setBackup(validator.getBackup());
        this.getRepository().save(e);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success update backup email");
    }

    public void updatePreferences(UpdateEmailPreferencesValidator validator) {
        UserSecurity u = this.getUser();
        userSecurityFacade.updateEmailPreferences(u, EmailPreferencesEnum.valueOf(validator.getEmailPreferences()));
        userSecurityRepository.save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success update email preferences");
    }

}
