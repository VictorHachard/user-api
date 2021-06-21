package com.user.model.facades;

import com.user.model.entities.Email;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.PrivacyEnum;
import com.user.model.facades.commons.AbstractFacade;
import com.user.utils.Utils;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
// Lombok
@Log
public class EmailFacade extends AbstractFacade<Email> {

    public Email newInstance(String email, PriorityEnum pe, PrivacyEnum pe2) {
        Email res = super.newInstance();
        res.setEmail(email);
        res.setPriority(pe);
        res.setPrivacy(pe2);
        return res;
    }

    public void initToken(Email e) {
        e.setEmailConfirmed(false);
        String token;
        do { token = Utils.generateNewToken(48); } while (emailRepository.existsByEmailConfirmedToken(token));
        e.setEmailConfirmedToken(token);
        e.setEmailConfirmedSet(new Timestamp(System.currentTimeMillis()));
        e.setEmailConfirmedAt(null);
        log.info("The validation token for " + e.getEmail() + " is " + e.getEmailConfirmedToken()
                + ", the link is: http://localhost:4200/confirm/email/" + e.getEmailConfirmedToken());
    }

    public void confirmToken(Email e) {
        e.setEmailConfirmed(true);
        e.setEmailConfirmedToken(null);
        e.setEmailConfirmedSet(null);
        e.setEmailConfirmedAt(new Timestamp(System.currentTimeMillis()));
    }

}
