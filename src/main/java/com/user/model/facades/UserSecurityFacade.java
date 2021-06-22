package com.user.model.facades;

import com.user.model.entities.Email;
import com.user.model.entities.Password;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.EmailPreferencesEnum;
import com.user.model.entities.enums.PrivacyEnum;
import com.user.model.facades.commons.AbstractFacade;
import com.user.utils.Utils;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
// Lombok
@Log
public class UserSecurityFacade extends AbstractFacade<UserSecurity> {

    public UserSecurity newInstance(Email e, Password p, String u) {
        UserSecurity res = super.newInstance();
        res.setUsername(u);
        res.addPassword(p);
        res.addEmail(e);

        res.setPrivacy(PrivacyEnum.PRIVATE);
        res.setEmailPreferences(EmailPreferencesEnum.ALL);
        res.setTheme(themeRepository.findAll().get(0));
        return res;
    }

    public void updateInstance(UserSecurity u, String firstName, String middleName, String lastName, String biography, String url, String profileImageUrl) {
        u.setMiddleName(middleName);
        u.setLastName(lastName);
        u.setFirstName(firstName);
        u.setBiography(biography);
        u.setProfileImageUrl(profileImageUrl);
        u.setUrl(url);
    }

    public void updateEmailPreferences(UserSecurity u, EmailPreferencesEnum ep) {
        u.setEmailPreferences(ep);
    }

    public void updateInstance(UserSecurity u, String username) {
        u.setUsername(username);
    }

    public void initToken(UserSecurity u) {
        String token;
        do { token = Utils.generateNewToken(48); } while (userSecurityRepository.existsByPasswordResetToken(token));
        u.setPasswordResetToken(token);
        u.setPasswordResetSet(new Timestamp(System.currentTimeMillis()));
        u.setAuthTokenCreatedAt(null);
        log.info("The reset token for the user password is " + u.getPasswordResetToken()
                + ", the link is: http://localhost:4200/reset/email/" + u.getPasswordResetToken());
    }

    public void confirmToken(UserSecurity u) {
        u.setPasswordResetToken(null);
        u.setPasswordResetSet(null);
        u.setAuthTokenCreatedAt(new Timestamp(System.currentTimeMillis()));
    }

}
