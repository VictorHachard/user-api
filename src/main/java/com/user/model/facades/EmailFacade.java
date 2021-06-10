package com.user.model.facades;

import com.user.model.entities.Email;
import com.user.model.entities.PriorityEnum;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class EmailFacade extends AbstractFacade<Email> {

    public Email newInstance(String email, PriorityEnum pe) {
        Email res = super.newInstance();
        res.setEmail(email);
        res.setPriority(pe);
        return res;
    }

}
