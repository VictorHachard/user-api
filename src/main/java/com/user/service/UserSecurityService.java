package com.user.service;

import com.user.dto.CookieRememberDto;
import com.user.dto.UserSecurityDto;
import com.user.model.entities.*;
import com.user.model.entities.enums.PriorityEnum;
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
        boolean existsByEmailPriority = emailRepository.existsByEmailAndPriority(emailLower, PriorityEnum.PRINCIPAL);
        boolean existsByUsername = this.getRepository().existsByUsername(username);
        if (existsByUsername) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This username is already in the database");
        } else if (existsByEmailPriority) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This email is already define in the database as PRINCIPAL");
        }
        Email e = emailService.create(emailLower, PriorityEnum.PRINCIPAL);
        Password p = passwordService.create(password);

        UserSecurity user = userSecurityFacade.newInstance(e, p, username);
        this.getRepository().save(user);
        return user;
    }

    public UserSecurityDto login(String username, String password) {
        if (!this.getRepository().existsByUsername(username)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The username is not correct");
        }
        UserSecurity user = this.getRepository().findByUsername(username).get();
        List<Password> passwordList = new ArrayList<>(user.getPasswordList());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, passwordList.get(passwordList.size() -1).getPassword())) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The password is not correct");
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

    private void setAuthToken(UserSecurity u) {
        String token;
        do { token = Utils.generateNewToken(48); } while (userSecurityRepository.existsByAuthToken(token));
        u.setAuthTokenCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.getRepository().save(u);
    }

    public void logOut() {
    }

    public void addEmail(EmailValidator validator) {
        if (PriorityEnum.valueOf(validator.getPriorityEnum()) == PriorityEnum.PRINCIPAL) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "You cannot define a new PRINCIPAL email, only one PRINCIPAL email");
        }
        UserSecurity u = this.getUser();
        Email e = emailService.create(validator.getEmail().toLowerCase(), PriorityEnum.SECONDARY);
        u.addEmail(e);
        this.getRepository().save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success new email added");
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
        //TODO remove from user
        emailService.delete(id);
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
    }

    public void setToken(Users user) {
        user.setToken(Utils.generateNewToken(60));
        user.setTokenCreatedAt(new Timestamp(System.currentTimeMillis()));
    }*/

    /* Actions */

    public void actionConfirmEmail(String token) {
        emailService.confirmEmail(token);
    }

    public void actionResetPassword(String token) {
        if (!this.getRepository().existsByPasswordResetToken(token)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This token is doesn't not exist");
        }
        UserSecurity u = this.getRepository().findByPasswordResetToken(token).get();
        userSecurityFacade.confirmToken(u);
        this.getRepository().save(u);
    }

    public void actionForgetPassword(String usernameOrEmail) {
        if (!this.getRepository().existsByEmailOrUsername(usernameOrEmail)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "Cannot find an user witch this email or username");
        }
        UserSecurity u = this.getRepository().findByEmailOrUsername(usernameOrEmail).get();
        //TODO check not the first time
        userSecurityFacade.initToken(u);
        this.getRepository().save(u);
    }

}
