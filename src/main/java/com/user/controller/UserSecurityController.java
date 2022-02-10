package com.user.controller;

import com.user.interceptor.Authorisation;
import com.user.interceptor.AuthorisationForOverride;
import com.user.interceptor.AuthorisationForOverrideColumn;
import com.user.controller.commons.AbstractController;
import com.user.dto.SecurityLogDto;
import com.user.dto.UserSecurityDto;
import com.user.dto.UserSecurityProfileDto;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.RoleEnum;
import com.user.service.UserSecurityService;
import com.user.validator.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
// Authorisation
@AuthorisationForOverrideColumn(table = {
    @AuthorisationForOverride(name = "count", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
    @AuthorisationForOverride(name = "delete", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
    @AuthorisationForOverride(name = "getDto", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
    @AuthorisationForOverride(name = "getAllDto", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
    @AuthorisationForOverride(name = "get", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
    @AuthorisationForOverride(name = "getAll", roles = {RoleEnum.ROLE_ADMINISTRATOR})
})
public class UserSecurityController extends AbstractController<UserSecurity, UserSecurityDto> {

    @PostMapping("create")
    public void create(@Valid @RequestBody UserSecurityValidator validator) {
        this.getService().create(validator);
    }

    @PostMapping("login")
    public UserSecurityDto login(@Valid @RequestBody UserLoginValidator validator, @RequestHeader Map<String, String> headers) {
        //TODO Add a login throttling. A short time delay that increases with the number of failed attempts.
        UserSecurityService service = (UserSecurityService) this.getService();
        String token = new String(Base64.getDecoder().decode(validator.getAuth()));
        UserSecurityDto res = service.login(headers,
                token.substring(0, token.indexOf(":")),
                token.substring(token.indexOf(":") + 1), validator.isRememberMe(), validator.getCode());
        return res;
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("login/update")
    public UserSecurityDto loginUpdate() {
        UserSecurityService service = ((UserSecurityService) this.getService());
        UserSecurityDto res = service.loginUpdate();
        return res;
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("logout")
    public void logout() {
        UserSecurityService service = ((UserSecurityService) this.getService());
        service.logout();
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("update/appearance/{appearanceId}")
    public void updateAppearance(@PathVariable("appearanceId") long appearanceId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateAppearance(appearanceId);
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("update/profile")
    public void updateProfile(@Valid @RequestBody UpdateProfileValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateProfile(validator);
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("update/profile-privacy")
    public void updateProfilePrivacy(@Valid @RequestBody UpdateProfilePrivacyValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateProfilePrivacy(validator);
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("update/two-factor/email")
    public void updateTwoFactorEmail(@Valid @RequestBody UpdateTwoFactorEmailValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateTwoFactorEmail(validator);
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("update/username")
    public void updateProfile(@Valid @RequestBody UpdateUsernameValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.updateUsername(validator);
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("add/password")
    public void addPassword(@Valid @RequestBody PasswordValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.addPassword(validator);
    }

    @Authorisation(roles = {RoleEnum.ROLE_ADMINISTRATOR})
    @PostMapping("add/role/{roleId}/user/{userId}")
    public void addRole(@PathVariable("roleId") long roleId, @PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.addRole(roleId, userId);
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("add/blocked-user/{userId}")
    public void addBlockedUser(@PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.addBlockedUser(userId);
    }

    @Authorisation(roles = {RoleEnum.ROLE_ADMINISTRATOR})
    @PostMapping("add/group/{groupId}/user/{userId}")
    public void addGroup(@PathVariable("groupId") long groupId, @PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.addGroup(groupId, userId);
    }

    @Authorisation(roles = {RoleEnum.ROLE_ADMINISTRATOR})
    @DeleteMapping("remove/role/{roleId}/user/{userId}")
    public void removeRole(@PathVariable("roleId") long roleId, @PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeRole(roleId, userId);
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @DeleteMapping("remove/blocked-user/{userId}")
    public void removeBlockedUser(@PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeBlockedUser(userId);
    }

    @Authorisation(roles = {RoleEnum.ROLE_ADMINISTRATOR})
    @DeleteMapping("remove/group/{groupId}/user/{userId}")
    public void removeGroup(@PathVariable("groupId") long groupId, @PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeGroup(groupId, userId);
    }

    /* Get */

    @Authorisation(roles = {RoleEnum.ROLE_USER})
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

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @GetMapping("dto/profile/{username}")
    public UserSecurityProfileDto getUserSecurityProfileDto(@PathVariable("username") String username) {
        UserSecurityService service = ((UserSecurityService) this.getService());
        return service.getUserSecurityProfileDto(username);
    }

    /* Actions */

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
