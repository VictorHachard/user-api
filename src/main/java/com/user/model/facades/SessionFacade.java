package com.user.model.facades;

import com.user.model.entities.Session;
import com.user.model.facades.commons.AbstractFacade;
import com.user.utils.Utils;
import org.springframework.stereotype.Component;

@Component
public class SessionFacade extends AbstractFacade<Session> {

    public Session newInstance(String ip, boolean onMobile, boolean rememberMe) {
        Session res = super.newInstance();
        String token;
        do {
            token = Utils.generateNewToken(48);
        } while (sessionRepository.existsByToken(token));
        res.setToken(token);
        res.setOnMobile(onMobile);
        res.setRememberMe(rememberMe);
        res.setIp(ip);
        return res;
    }

}
