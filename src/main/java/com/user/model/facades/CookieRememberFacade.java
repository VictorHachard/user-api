package com.user.model.facades;

import com.user.model.entities.CookieRemember;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class CookieRememberFacade extends AbstractFacade<CookieRemember> {

    public CookieRemember newInstance(String token) {
        CookieRemember res = super.newInstance();
        res.setToken(token);
        return res;
    }

}
