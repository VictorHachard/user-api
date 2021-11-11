package com.user.model.facades;

import com.user.model.entities.SecurityLog;
import com.user.model.entities.Session;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.SecurityLogEnum;
import com.user.model.facades.commons.AbstractFacade;
import com.user.utils.Utils;
import org.springframework.stereotype.Component;

@Component
public class SessionFacade extends AbstractFacade<Session> {

    public Session newInstance() {
        Session res = super.newInstance();
        String token;
        do {
            token = Utils.generateNewToken(48);
        } while (sessionRepository.existsByToken(token));
        res.setToken(token);
        return res;
    }

}
