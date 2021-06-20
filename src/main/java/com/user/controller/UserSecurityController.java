package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.CookieRememberDto;
import com.user.dto.UserSecurityDto;
import com.user.model.entities.UserSecurity;
import com.user.service.UserSecurityService;
import com.user.validator.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;

@RestController
@RequestMapping("api/v1/user/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class UserSecurityController extends AbstractController<UserSecurity, UserSecurityDto> {

    @PostMapping("/create")
    public void create(@Valid @RequestBody UserSecurityValidator validator) {
        this.getService().create(validator);
    }

    @PostMapping("login")
    public UserSecurityDto login(@Valid @RequestBody String auth) {
        UserSecurityService service = (UserSecurityService) this.getService();
        String token = new String(Base64.getDecoder().decode(auth));
        UserSecurityDto res = service.login(token.substring(0, token.indexOf(":")),
                token.substring(token.indexOf(":") + 1));
        return res;
    }

    @PostMapping("login/cookie")
    public UserSecurityDto loginCookie(@Valid @RequestBody LoginFromCookieValidator validator) {
        UserSecurityService service = ((UserSecurityService) this.getService());
        UserSecurityDto res = service.connectCookie(validator);
        return res;
    }

    @PostMapping("logout")
    public void loginCookie(@Valid @RequestBody String authToken) {
        UserSecurityService service = ((UserSecurityService) this.getService());
        service.logOut(authToken);
    }

    @PostMapping("add/email")
    public void addEmail(@Valid @RequestBody EmailValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.addEmail(validator);
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

    @PostMapping("add/group/{groupId}/user/{userId}")
    public void addGroup(@PathVariable("groupId") long groupId, @PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.addGroup(groupId, userId);
    }

    @PostMapping("remove/role/{roleId}/user/{userId}")
    public void removeRole(@PathVariable("roleId") long roleId, @PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeRole(roleId, userId);
    }

    @PostMapping("remove/group/{groupId}/user/{userId}")
    public void removeGroup(@PathVariable("groupId") long groupId, @PathVariable("userId") long userId) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeGroup(groupId, userId);
    }

    @PostMapping("remove/email/{id}")
    public void removeEmail(@PathVariable("id") long id) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeEmail(id);
    }

    @PostMapping("remove/cookie/{id}")
    public void removeCookie(@PathVariable("id") long id) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeCookie(id);
    }

    public void addPassword() {

    }

    /* Actions */

    @PostMapping("action/confirm/email")
    public void actionConfirmEmail(@Valid @RequestBody String token) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.actionConfirmEmail(token);
    }

    @PostMapping("action/reset/password")
    public void actionResetPassword(@Valid @RequestBody String token) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.actionResetPassword(token);
    }

    @PostMapping("action/forget/password")
    public void actionForgetPassword(@Valid @RequestBody String usernameOrEmail) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.actionForgetPassword(usernameOrEmail);
    }

}
