package com.user.service;

import com.user.model.entities.Email;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.PrivacyEnum;
import com.user.model.repositories.EmailRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.EmailValidator;
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
        this.create(validator.getEmail(), PriorityEnum.SECONDARY);
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
        } else if (this.getRepository().findById(id).get().getPriority() == PriorityEnum.PRINCIPAL) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "Cannot delete PRINCIPAL email");
        } else {
            this.getRepository().deleteById(id);
        }
    }

    public void resendConfirmEmail(Email e) {
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

    public void updateEmailPriority(String es) {
        if (!this.getRepository().existsByEmail(es)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This email doesn't exist");
        }
        Email e = this.getRepository().findByEmail(es).get();
        e.setPriority(PriorityEnum.PRINCIPAL);
        this.getRepository().save(e);
    }

}
