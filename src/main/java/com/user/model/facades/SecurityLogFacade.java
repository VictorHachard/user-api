package com.user.model.facades;

import com.user.model.entities.SecurityLog;
import com.user.model.entities.Session;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.SecurityLogEnum;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class SecurityLogFacade extends AbstractFacade<SecurityLog> {

    public SecurityLog newInstance(SecurityLogEnum sl, UserSecurity us, Session se, String info) {
        SecurityLog res = super.newInstance();
        res.setSecurityLog(sl);
        res.setUserSecurity(us);
        res.setInfo(info);
        res.setSession(se);
        return res;
    }

}
