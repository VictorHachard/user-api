package com.user.model.facades;

import com.user.model.entities.Email;
import com.user.model.entities.Password;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.PrivacyEnum;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityFacade extends AbstractFacade<UserSecurity> {

    public UserSecurity newInstance(Email e, Password p, String u) {
        UserSecurity res = super.newInstance();
        res.setUsername(u);
        res.addPassword(p);
        res.addEmail(e);

        res.setPrivacy(PrivacyEnum.PRIVATE);
        return res;
    }

}
