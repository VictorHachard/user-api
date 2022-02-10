package com.user.model.facades;

import com.user.model.entities.Session;
import com.user.model.facades.commons.AbstractFacade;
import com.user.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SessionFacade extends AbstractFacade<Session> {

    public Session newInstance(String ip, Map<String, String> headers, boolean rememberMe) {
        Session res = super.newInstance();
        String token;
        do {
            token = Utils.generateNewToken(48);
        } while (sessionRepository.existsByToken(token));
        res.setToken(token);
        res.setRememberMe(rememberMe);
        res.setIp(ip);
        res.setPlatform(headers.get("sec-ch-ua-platform"));
        res.setUserAgent(headers.get("user-agent"));
        res.setOnMobile(headers.get("user-agent").contains("Mobi"));
        return res;
    }

}
