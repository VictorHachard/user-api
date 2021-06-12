package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.UserSecurityDto;
import com.user.model.entities.UserSecurity;
import com.user.service.UserSecurityService;
import com.user.validator.EmailValidator;
import com.user.validator.LoginFromCookieValidator;
import com.user.validator.UserSecurityValidator;
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
    public UserSecurityDto login(@RequestHeader("Authentification") String auth) {
        UserSecurityService service = (UserSecurityService) this.getService();
        String token = new String(Base64.getDecoder().decode(auth));
        token = token.replace("Basic ", "");

        UserSecurityDto res = service.login(token.substring(0, token.indexOf(":")),
                token.substring(token.indexOf(":") + 1));

        log.info("LOGIN " + res.getAuthToken() + " is the token of user " + res.getUsername());
        return res;
    }

    @PostMapping("loginFromCookie")
    public UserSecurityDto loginFromCookie(@Valid @RequestBody LoginFromCookieValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();

        UserSecurityDto res = service.connectFromCookie(validator);

        log.info("LOGIN " + res.getAuthToken() + " is the token of user " + res.getUsername());
        return res;
    }

    @PostMapping("add/email")
    public void addEmail(@Valid @RequestBody EmailValidator validator) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.addEmail(validator);
    }

    @PostMapping("remove/email/{id}")
    public void removeEmail(@PathVariable("id") long id) {
        UserSecurityService service = (UserSecurityService) this.getService();
        service.removeEmail(id);
    }

    public void addPassword() {

    }

}
