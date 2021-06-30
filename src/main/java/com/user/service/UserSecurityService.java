package com.user.service;

import com.user.dto.CookieRememberDto;
import com.user.dto.SecurityLogDto;
import com.user.dto.UserSecurityDto;
import com.user.dto.UserSecurityProfileDto;
import com.user.model.entities.*;
import com.user.model.entities.enums.EmailPreferencesEnum;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.PrivacyEnum;
import com.user.model.entities.enums.SecurityLogEnum;
import com.user.model.repositories.UserSecurityRepository;
import com.user.service.commons.AbstractService;
import com.user.utils.Utils;
import com.user.validator.*;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class UserSecurityService extends AbstractService<UserSecurity, UserSecurityRepository> {

    @Override
    public Page<UserSecurity> getAllBy(Pageable pageable, String searchBy, String searchValue) {
        switch (searchBy) {
            case "username":
                return this.getRepository().findByUsernameContaining(searchValue, pageable);
            case "null":
                return super.getAllBy(pageable, searchBy, searchValue);
            default:
                this.responseStatus(HttpStatus.BAD_REQUEST, "By " + searchBy + " is incorrect");
        }
        return null;
    }

    @Override
    public void create(AbstractValidator abstractValidator) {
        UserSecurityValidator validator = (UserSecurityValidator) abstractValidator;
        this.create(validator.getUsername(), validator.getEmail(), validator.getPassword());
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }

    public UserSecurity create(String username, String email, String password) {
        String emailLower = email.toLowerCase();
        boolean existsByEmailPriority = emailRepository.existsByEmailAndPriority(emailLower, PriorityEnum.PRIMARY);
        boolean existsByUsername = this.getRepository().existsByUsername(username);
        if (existsByUsername) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This username is already in the database");
        } else if (existsByEmailPriority) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This email is already define in the database as PRINCIPAL");
        }
        Email e = emailService.create(emailLower, PriorityEnum.PRIMARY);
        Password p = passwordService.create(password);

        UserSecurity user = userSecurityFacade.newInstance(e, p, username);
        this.getRepository().save(user);
        return user;
    }

    public UserSecurityDto login(String username, String password, String code) {
        if (!this.getRepository().existsByUsername(username)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The username is not correct");
        }
        UserSecurity user = this.getRepository().findByUsername(username).get();

        List<Password> passwordList = new ArrayList<>(user.getPasswordList());
        Collections.sort(passwordList);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, passwordList.get(passwordList.size() -1).getPassword())) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The password is not correct");
        }

        Date currentDate = new Date();
        if (user.getTwoFactorEmail()) {
            if ((code == null || code.equals("")) && (user.getAuthTokenCreatedAt() == null || user.getAuthTokenCreatedAt().before(new Date(currentDate.getTime() - 1l * 24 * 60 * 60 * 1000)))) {
                userSecurityFacade.initTwoFactorEmailToken(user);
                this.getRepository().save(user);
                this.responseStatus(HttpStatus.BAD_REQUEST, "2FA - Need two-factor authentication");
            } else if (code != null && !code.equals("") && user.getTwoFactorEmailCode() != null && !user.getTwoFactorEmailCode().equals("") && !user.getTwoFactorEmailCode().equals(code)) {
                this.responseStatus(HttpStatus.BAD_REQUEST, "Two-factor authentication code is not correct");
            }
        }
        if (user.getTwoFactorEmailCode() != null) {
            userSecurityFacade.confirmTwoFactorEmailToken(user);
            this.getRepository().save(user);
        }
        this.setAuthToken(user);
        return (UserSecurityDto) this.getMapper().getDto(user);
    }

    public UserSecurityDto connectCookie(LoginFromCookieValidator validator) {
        if (!this.getRepository().existsByCookieRemember(validator.getToken())) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The token is not correct");
        }
        UserSecurity user = this.getRepository().findByCookieRemember(validator.getToken()).get();
        //TODO check if token not expired
        this.setAuthToken(user);
        return (UserSecurityDto) this.getMapper().getDto(user);
    }

    public UserSecurityDto loginUpdate() {
        return (UserSecurityDto) getMapper().getDto(this.getUser());
    }

    public void updateTwoFactorEmail(UpdateTwoFactorEmailValidator validator) {
        UserSecurity u = this.getUser();
        if (!u.getPrincipalEmail().getEmailConfirmed()) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The principal email is not confirmed");
        }
        userSecurityFacade.updateTwoFactorEmail(u, validator.getActive());
        this.getRepository().save(u);
    }

    private void setAuthToken(UserSecurity u) {
        //If the token is not older then 1 day return the same token
        Date currentDate = new Date();
        if (u.getAuthToken() == null || u.getAuthToken().equals("") || u.getAuthTokenCreatedAt() == null || u.getAuthTokenCreatedAt().before(new Date(currentDate.getTime() - 1l * 24 * 60 * 60 * 1000))) {
            String token;
            do { token = Utils.generateNewToken(48); } while (userSecurityRepository.existsByAuthToken(token));
            u.setAuthToken(token);
            u.setAuthTokenCreatedAt(new Timestamp(System.currentTimeMillis()));
        }
        u.setLastConnection(new Timestamp(System.currentTimeMillis()));
        log.info("LOGIN " + u.getAuthToken() + " is the token of user " + u.getUsername());
        this.getRepository().save(u);
    }

    public void logOut() {
        UserSecurity u = this.getUser();
        u.setAuthToken(null);
        u.setAuthTokenCreatedAt(null);
        log.info("LOGOUT of user " + u.getUsername());
    }

    public void addEmail(EmailValidator validator) {
        UserSecurity u = this.getUser();
        Email e = emailService.create(validator.getEmail().toLowerCase(), PriorityEnum.SECONDARY);
        u.addEmail(e);
        this.getRepository().save(u);
        securityLogService.create(SecurityLogEnum.EMAIL_ADDED, u, "Email " + e.getEmail() + " added");
        this.responseStatus(HttpStatus.NO_CONTENT, "Success new email added");
    }

    public void updateEmailPriority(EmailValidator validator) {
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
        emailService.updateEmailPriority(validator.getEmail());
        this.getRepository().save(u);
        securityLogService.create(SecurityLogEnum.EMAIL_CHANGE_PRIORITY, u, "Email " + validator.getEmail() + " updated to Principal");
        this.responseStatus(HttpStatus.NO_CONTENT, "Success new email added");
    }

    public void updateProfile(UpdateProfileValidator validator) {
        UserSecurity u = this.getUser();
        userSecurityFacade.updateInstance(u, validator.getFirstName(), validator.getMiddleName(), validator.getLastName(), validator.getBiography(), validator.getUrl(), validator.getProfileImage());
        this.getRepository().save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success update user");
    }

    public void updateEmailPreferences(UpdateEmailPreferencesValidator validator) {
        UserSecurity u = this.getUser();
        userSecurityFacade.updateEmailPreferences(u, EmailPreferencesEnum.valueOf(validator.getEmailPreferences()));
        this.getRepository().save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success update email preferences");
    }

    public void updateProfilePrivacy(UpdateProfilePrivacyValidator validator) {
        UserSecurity u = this.getUser();
        userSecurityFacade.updateProfilePrivacy(u, PrivacyEnum.valueOf(validator.getProfilePrivacy()));
        this.getRepository().save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success update privacy");
    }

    public void updateUsername(UpdateUsernameValidator validator) {
        UserSecurity u = this.getUser();
        boolean existsByUsername = this.getRepository().existsByUsername(validator.getUsername());
        if (existsByUsername) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This username is already in the database");
        }
        userSecurityFacade.updateInstance(u, validator.getUsername());
        this.getRepository().save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success update user");
    }

    public void updateAppearance(long id) {
        UserSecurity u = this.getUser();
        u.setTheme(themeService.get(id));
        this.getRepository().save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success update appearance");
    }

    public void updateEmailBackup(long id, UpdateEmailBackupValidator validator) {
        UserSecurity u = this.getUser();
        boolean contain = false;
        Email e = null;
        for (Email ee : u.getEmailList()) {if (ee.getId() == id) {contain = true; e = ee; break;}}
        if (!contain) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The user don't have this email");
        }
        e.setBackup(validator.getBackup());
        emailRepository.save(e);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success update backup email");
    }

    public void addPassword(PasswordValidator validator) {
        UserSecurity u = this.getUser();
        Password p = passwordService.create(validator.getPassword());
        u.addPassword(p);
        this.getRepository().save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success new password added");
    }

    public CookieRememberDto addCookie(CookieRememberValidator validator) {
        UserSecurity u = this.getUser();
        CookieRemember cr = cookieRememberService.create(validator);
        u.addCookie(cr);
        this.getRepository().save(u);
        return cookieRememberMapper.getDto(cr);
    }

    public void addGroup(long groupId, long userId) {
        Group g = groupService.get(groupId);
        UserSecurity u = this.get(userId);
        if (u.getGroupList().contains(g)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This group is already define for this user");
        }
        u.addGroup(g);
        this.getRepository().save(u);
    }

    public void addRole(long roleId, long userId) {
        Role r = roleService.get(roleId);
        UserSecurity u = this.get(userId);
        if (u.getPermissionList().contains(r)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This role is already define for this user");
        }
        u.addRole(r);
        this.getRepository().save(u);
    }

    public void removeGroup(long groupId, long userId) {
        Group g = groupService.get(groupId);
        UserSecurity u = this.get(userId);
        if (!u.getGroupList().contains(g)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This group is not define for this user");
        }
        u.getGroupList().remove(g);
        this.getRepository().save(u);
    }

    public void removeRole(long roleId, long userId) {
        Role r = roleService.get(roleId);
        UserSecurity u = this.get(userId);
        if (!u.getPermissionList().contains(r)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This role is not define for this user");
        }
        u.getPermissionList().remove(r);
        this.getRepository().save(u);
    }

    public void removeEmail(long id) {
        if (!emailRepository.existsById(id)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        }
        Email e = emailRepository.findById(id).get();
        if (e.getPriority() == PriorityEnum.PRIMARY) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "Cannot delete PRINCIPAL email");
        } else {
            UserSecurity u = this.getRepository().findByEmailId(id).get();
            u.getEmailList().remove(e);
            emailRepository.deleteById(id);
            securityLogService.create(SecurityLogEnum.EMAIL_REMOVED, u, "Email " + e.getEmail() + " removed");
            this.responseStatus(HttpStatus.NO_CONTENT, "Email deleted");
        }
    }

    public void removeCookie(long id) {
        CookieRemember cr = cookieRememberService.get(id);
        UserSecurity u = this.getUser();
        u.getCookieList().remove(cr);
        cookieRememberService.delete(id);
    }

    /*public boolean checkToken(Users user, String token) {
        return user.getToken() != null
                && user.getToken().equals(token)
                && Date.from(Instant.now().minusSeconds(86400)).after(user.getTokenCreatedAt());
    }*/

    public List<SecurityLogDto> getAllSecurityLogDto(Integer pageIndex, Integer pageSize) {
        UserSecurity u = this.getUser();
        return securityLogMapper.getAllDto(securityLogService.getAllDtoByUser(u, pageIndex, pageSize).getContent());
    }

    public UserSecurityProfileDto getUserSecurityProfileDto(String username) {
        if (!this.getRepository().existsByUsername(username)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The username is not correct");
        }
        return userSecurityMapper.getProfileDto(this.getRepository().findByUsername(username).get());
    }

    /* Actions */

    public void actionConfirmEmail(String token) {
        emailService.confirmEmail(token);
    }

    public void actionConfirmResendEmail(long id) {
        if (!this.getRepository().existsByEmailId(id)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The email is not correct");
        }
        UserSecurity u = this.getUser();
        boolean contain = false;
        for (Email ee : u.getEmailList()) {if (ee.getId() == id) {contain = true; break;}}
        if (!contain) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The user don't have this email");
        }
        emailService.resendConfirmEmail(emailService.get(id));
    }

    public void actionResetPassword(ActionResetPasswordValidator validator) {
        if (!this.getRepository().existsByPasswordResetToken(validator.getToken())) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This token is doesn't not exist");
        }
        UserSecurity u = this.getRepository().findByPasswordResetToken(validator.getToken()).get();
        userSecurityFacade.confirmPasswordToken(u);
        Password p = passwordService.create(validator.getPassword());
        u.addPassword(p);
        passwordRepository.save(p);
        this.getRepository().save(u);
    }

    public void actionForgetPassword(String usernameOrEmail) {
        if (!this.getRepository().existsByEmailOrUsername(usernameOrEmail)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "Cannot find an user witch this email or username");
        }
        UserSecurity u = this.getRepository().findByEmailOrUsername(usernameOrEmail).get();
        //TODO check not the first time
        userSecurityFacade.initPasswordToken(u);
        this.getRepository().save(u);
        //securityLogService.create(SecurityLogEnum.PASSWORD_CHANGE, u, "Email sent to " + u.getEmailList().stream().filter(email -> {return email.getPriority().equals(PriorityEnum.PRIMARY);}).collect(Collectors.toList()).get(0) + " for a password reset");
        this.responseStatus(HttpStatus.NO_CONTENT, "Success password forget");
    }

    public void actionSetPassword(SetPasswordValidator validator) {
        UserSecurity u = this.getUser();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<Password> passwordList = new ArrayList<>(u.getPasswordList());
        Collections.sort(passwordList);

        if (!passwordEncoder.matches(validator.getOldPassword(), passwordList.get(passwordList.size() -1).getPassword())) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The password is not correct");
        }
        Password p = passwordService.create(validator.getNewPassword());
        u.addPassword(p);
        this.getRepository().save(u);
        securityLogService.create(SecurityLogEnum.PASSWORD_CHANGE, u, "Password update (no email)");
        this.responseStatus(HttpStatus.NO_CONTENT, "Success new password added");
    }

}
