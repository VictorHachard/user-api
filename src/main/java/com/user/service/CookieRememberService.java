package com.user.service;

import com.user.model.entities.CookieRemember;
import com.user.model.repositories.CookieRememberRepository;
import com.user.service.commons.AbstractService;
import com.user.utils.Utils;
import com.user.validator.CookieRememberValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class CookieRememberService extends AbstractService<CookieRemember, CookieRememberRepository> {

    public CookieRemember create(CookieRememberValidator validator) {
        String token;
        do { token = Utils.generateNewToken(48); } while (cookieRememberRepository.existsByToken(token));
        CookieRemember cr = cookieRememberFacade.newInstance(token);
        this.getRepository().save(cr);
        return cr;
    }

}
