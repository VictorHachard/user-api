package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.CookieRememberDto;
import com.user.dto.SecurityLogDto;
import com.user.dto.UserSecurityDto;
import com.user.dto.UserSecurityProfileDto;
import com.user.model.entities.UserSecurity;
import com.user.service.UserSecurityService;
import com.user.validator.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("api/v1/user/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class UserSecurityController extends AbstractController<UserSecurity, UserSecurityDto> {

    @PostMapping("create")
    public void create(@Valid @RequestBody UserSecurityValidator validator) {
        this.getService().create(validator);
    }

    @PostMapping("login")
    public UserSecurityDto login(@Valid @RequestBody UserLoginValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        String token = new String(Base64.getDecoder().decode(validator.getAuth()));
        UserSecurityDto res = service.login(token.substring(0, token.indexOf(":")),
                token.substring(token.indexOf(":") + 1), validator.getCode());
        return res;
    }

    @PostMapping("login/cookie")
    public UserSecurityDto loginCookie(@Valid @RequestBody LoginFromCookieValidator validator) {
        UserSecurityService service = ((UserSecurityService) this.getService());
        UserSecurityDto res = service.connectCookie(validator);
        return res;
    }

    @PostMapping("login/update")
    public UserSecurityDto loginUpdate() {
        UserSecurityService service = ((UserSecurityService) this.getService());
        UserSecurityDto res = service.loginUpdate();
        return res;
    }

    @PostMapping("logout")
    public void logout() {
        UserSecurityService service = ((UserSecurityService) this.getService());
        service.logOut();
    }

    @PostMapping("add/email")
    public void addEmail(@Valid @RequestBody EmailValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.addEmail(validator);
    }

    @PostMapping("update/email/priority")
    public void updateEmailPriority(@Valid @RequestBody EmailValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateEmailPriority(validator);
    }

    @PostMapping("update/email/backup/{emailId}")
    public void updateEmailBackup(@Valid @RequestBody UpdateEmailBackupValidator validator, @PathVariable("emailId") long emailId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateEmailBackup(emailId, validator);
    }

    @PostMapping("update/appearance/{appearanceId}")
    public void updateAppearance(@PathVariable("appearanceId") long appearanceId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateAppearance(appearanceId);
    }

    @PostMapping("update/profile")
    public void updateProfile(@Valid @RequestBody UpdateProfileValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateProfile(validator);
    }

    @PostMapping("update/email-preferences")
    public void updateEmailPreferences(@Valid @RequestBody UpdateEmailPreferencesValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateEmailPreferences(validator);
    }

    @PostMapping("update/profile-privacy")
    public void updateProfilePrivacy(@Valid @RequestBody UpdateProfilePrivacyValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateProfilePrivacy(validator);
    }

    @PostMapping("update/two-factor/email")
    public void updateTwoFactorEmail(@Valid @RequestBody UpdateTwoFactorEmailValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateTwoFactorEmail(validator);
    }

    @PostMapping("update/username")
    public void updateProfile(@Valid @RequestBody UpdateUsernameValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateUsername(validator);
    }

    @PostMapping("add/password")
    public void addPassword(@Valid @RequestBody PasswordValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.addPassword(validator);
    }

    @PostMapping("add/cookie")
    public CookieRememberDto addCookie(@Valid @RequestBody CookieRememberValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        return service.addCookie(validator);
    }

    @PostMapping("add/role/{roleId}/user/{userId}")
    public void addRole(@PathVariable("roleId") long roleId, @PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.addRole(roleId, userId);
    }

    @PostMapping("add/blocked-user/{userId}")
    public void addBlockedUser(@PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.addBlockedUser(userId);
    }

    @PostMapping("add/group/{groupId}/user/{userId}")
    public void addGroup(@PathVariable("groupId") long groupId, @PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.addGroup(groupId, userId);
    }

    @DeleteMapping("remove/role/{roleId}/user/{userId}")
    public void removeRole(@PathVariable("roleId") long roleId, @PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeRole(roleId, userId);
    }

    @DeleteMapping("remove/blocked-user/{userId}")
    public void removeBlockedUser(@PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeBlockedUser(userId);
    }

    @DeleteMapping("remove/group/{groupId}/user/{userId}")
    public void removeGroup(@PathVariable("groupId") long groupId, @PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeGroup(groupId, userId);
    }

    @DeleteMapping("remove/email/{id}")
    public void removeEmail(@PathVariable("id") long id) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeEmail(id);
    }

    @DeleteMapping("remove/cookie/{id}")
    public void removeCookie(@PathVariable("id") long id) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeCookie(id);
    }

    /* Get */

    @GetMapping("dto/security-log")
    public List<SecurityLogDto> getAllSecurityLogDto(@RequestParam(defaultValue = "0") Integer pageIndex,
                                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        UserSecurityService service = ((UserSecurityService) this.getService());
        return service.getAllSecurityLogDto(pageIndex, pageSize);
    }

    /*@GetMapping("dto/profile")
    public List<UserSecurityProfileDto> getAllUserSecurityProfileDto(@RequestParam(defaultValue = "0") Integer pageIndex,
                                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        UserSecurityService service = ((UserSecurityService) this.getService());
        return service.getAllUserSecurityProfileDto(pageIndex, pageSize);
    }*/

    @GetMapping("dto/profile/{username}")
    public UserSecurityProfileDto getUserSecurityProfileDto(@PathVariable("username") String username) {
        UserSecurityService service = ((UserSecurityService) this.getService());
        return service.getUserSecurityProfileDto(username);
    }

    /* Actions */

    @PostMapping("action/confirm/email")
    public void actionConfirmEmail(@Valid @RequestBody String token) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.actionConfirmEmail(token);
    }

    @PostMapping("action/confirm/resend/email/{emailId}")
    public void actionConfirmResendEmail(@PathVariable("emailId") long emailId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.actionConfirmResendEmail(emailId);
    }

    @PostMapping("action/reset/password")
    public void actionResetPassword(@Valid @RequestBody ActionResetPasswordValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.actionResetPassword(validator);
    }

    @PostMapping("action/forget/password")
    public void actionForgetPassword(@Valid @RequestBody String usernameOrEmail) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.actionForgetPassword(usernameOrEmail);
    }

    @PostMapping("action/set/password")
    public void setPassword(@Valid @RequestBody SetPasswordValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.actionSetPassword(validator);
    }

}
