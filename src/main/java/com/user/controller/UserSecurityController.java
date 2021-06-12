package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.UserSecurityDto;
import com.user.service.UserSecurityService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("api/v1/user/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class UserSecurityController extends AbstractController {

    @PostMapping("login")
    public UserSecurityDto login(@RequestHeader("Authentication") String auth) {
        UserSecurityService service = (UserSecurityService) this.getService();
        String token = new String(Base64.getDecoder().decode(auth.replace("Basic ", "")));

        UserSecurityDto res = service.login(token.substring(0, token.indexOf(":")),
                token.substring(token.indexOf(":") + 1));

        log.info("LOGIN " + res.getAuthToken() + " is the token of user " + res.getUsername());
        return res;
    }



    public void addEmail() {

    }

    public void addPassword() {

    }

}
