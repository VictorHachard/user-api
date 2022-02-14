package com.user.service;

import com.user.Environment;
import com.user.dto.SecurityLogDto;
import com.user.dto.SessionDto;
import com.user.dto.UserSecurityDto;
import com.user.dto.UserSecurityProfileDto;
import com.user.model.entities.*;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.PrivacyEnum;
import com.user.model.entities.enums.RoleEnum;
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
import java.util.*;

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
    public List<UserSecurityProfileDto> getAllDtoBlockedUser(Integer pageIndex, Integer pageSize, String sortBy, String orderBy, String searchBy, String searchValue) {
        return userSecurityMapper.getAllProfileDto(this.getAll(pageIndex, pageSize, sortBy, orderBy, searchBy, searchValue));
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
        Role r = roleRepository.findByRole(RoleEnum.ROLE_USER).get();
        Email e = emailService.create(emailLower, PriorityEnum.PRIMARY);
        Password p = passwordService.create(password);

        UserSecurity user = userSecurityFacade.newInstance(e, p, r, username);
        this.getRepository().save(user);
        return user;
    }

    public UserSecurityDto login(Map<String, String> headers, String username, String password, boolean rememberMe, String code) {
        if (!this.getRepository().existsByUsername(username)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The username is not correct");
        }
        UserSecurity user = this.getRepository().findByUsername(username).get();

        if (user.getLastConnectionAttempt() != null && user.getLastConnectionAttempt().after(new Date(new Date().getTime() - (long) 1000 * 60 * user.getFailConnectionAttempt()))) { // 1 minute * failedAttempts
            this.responseStatus(HttpStatus.BAD_REQUEST, "You have to wait " + user.getFailConnectionAttempt() + " minutes before you can try again");
        }

        List<Password> passwordList = new ArrayList<>(user.getPasswordList());
        Collections.sort(passwordList);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, passwordList.get(passwordList.size() -1).getPassword())) {
            user.setFailConnectionAttempt(user.getFailConnectionAttempt() + 1);
            user.setLastConnectionAttempt(new Timestamp(System.currentTimeMillis()));
            this.getRepository().save(user);
            this.responseStatus(HttpStatus.BAD_REQUEST, "The password is not correct");
        }

        Date currentDate = new Date();
        if (user.getTwoFactorEmail()) {
            if ((code == null || code.equals("")) /*&& (user.getAuthTokenCreatedAt() == null || user.getAuthTokenCreatedAt().before(new Date(currentDate.getTime() - 1l * 24 * 60 * 60 * 1000)))*/) {
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

        Session session = sessionService.create(rememberMe, headers);
        UserSecurityDto userSecurityDto = (UserSecurityDto) this.getMapper().getDto(user);
        user.addSession(session);
        String token = sessionService.setAuthToken(session);
        log.info("LOGIN " + token + " is the token of user " + user.getUsername());
        user.setLastConnection(new Timestamp(System.currentTimeMillis()));
        user.setFailConnectionAttempt(0);
        user.setLastConnectionAttempt(null);
        this.getRepository().save(user);
        SessionDto sessionDto = sessionMapper.getDto(session);
        sessionDto.setAuthToken(token);
        userSecurityDto.setActualSessionDto(sessionDto);
        return userSecurityDto;
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

    public void logout() {
        UserSecurity u = this.getUser();
        Session s = this.getSession();
        sessionService.delete(s);
        log.info("LOGOUT of user " + u.getUsername());
    }

    public void updateProfile(UpdateProfileValidator validator) {
        UserSecurity u = this.getUser();
        userSecurityFacade.updateInstance(u, validator.getFirstName(), validator.getMiddleName(), validator.getLastName(), validator.getBiography(), validator.getUrl(), validator.getProfileImage());
        this.getRepository().save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success update user");
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

    public void addPassword(PasswordValidator validator) {
        UserSecurity u = this.getUser();
        Password p = passwordService.create(validator.getPassword());
        u.addPassword(p);
        this.getRepository().save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success new password added");
    }

    public void addBlockedUser(long userId) {
        UserSecurity u = this.getUser();
        UserSecurity ub = this.get(userId);
        if (u.getBlockedUserSecurity().contains(ub)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This user is already blocked");
        }
        u.addBlockedUserSecurity(ub);
        this.getRepository().save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success blocked user added");
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

    public void removeBlockedUser(long userId) {
        UserSecurity ub = this.get(userId);
        UserSecurity u = this.getUser();
        if (!u.getBlockedUserSecurity().contains(ub)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This blocked user is not define for this user");
        }
        u.getBlockedUserSecurity().remove(ub);
        this.getRepository().save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success blocked user removed");
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

    public void actionResetPassword(ActionResetPasswordValidator validator) {
        String token = validator.getToken();
        String hashToken = Utils.hash256(token);
        if (!this.getRepository().existsByPasswordResetToken(hashToken)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This token is doesn't not exist");
        }
        UserSecurity u = this.getRepository().findByPasswordResetToken(hashToken).get();
        userSecurityFacade.confirmPasswordToken(u);
        Password p = passwordService.create(validator.getPassword());
        u.addPassword(p);
        passwordRepository.save(p);
        this.getRepository().save(u);
        securityLogService.create(SecurityLogEnum.PASSWORD_RESET, "Password updated with email");
    }

    public void actionForgetPassword(String usernameOrEmail) {
        if (!this.getRepository().existsByEmailOrUsername(usernameOrEmail)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "Cannot find an user witch this email or username");
        }
        UserSecurity u = this.getRepository().findByEmailOrUsername(usernameOrEmail).get();
        //TODO check not the first time, need confirmed account
        userSecurityFacade.initPasswordToken(u);
        this.getRepository().save(u);
        //securityLogService.create(SecurityLogEnum.PASSWORD_FORGET, u, "Email sent to " + u.getEmailList().stream().filter(email -> {return email.getPriority().equals(PriorityEnum.PRIMARY);}).collect(Collectors.toList()).get(0) + " for a password reset");
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
        securityLogService.create(SecurityLogEnum.PASSWORD_CHANGE, "Password updated");
        this.responseStatus(HttpStatus.NO_CONTENT, "Success new password added");
    }

}
