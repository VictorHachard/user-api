package com.user.model.facades;

import com.user.model.entities.Password;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class PasswordFacade extends AbstractFacade<Password> {

    public Password newInstance(String pa) {
        Password res = super.newInstance();
        res.setPassword(pa);
        return res;
    }

}
