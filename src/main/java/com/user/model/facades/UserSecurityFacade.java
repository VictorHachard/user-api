package com.user.model.facades;

import com.user.model.entities.Email;
import com.user.model.entities.Password;
import com.user.model.entities.Role;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.EmailPreferencesEnum;
import com.user.model.entities.enums.PrivacyEnum;
import com.user.model.entities.enums.RoleEnum;
import com.user.model.facades.commons.AbstractFacade;
import com.user.service.commons.FileStorageService;
import com.user.utils.Utils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
// Lombok
@Log
public class UserSecurityFacade extends AbstractFacade<UserSecurity> {

    public UserSecurity newInstance(Email e, Password p, Role r, String u) {
        UserSecurity res = super.newInstance();
        res.setUsername(u);
        res.addPassword(p);
        res.addEmail(e);
        res.addRole(r);
        res.setPrivacy(PrivacyEnum.PRIVATE);
        res.setTwoFactorEmail(false);
        res.setEmailPreferences(EmailPreferencesEnum.ALL);
        res.setTheme(themeRepository.findAll().get(0));
        return res;
    }

    @Autowired
    FileStorageService fileStorageService;

    public void updateInstance(UserSecurity u, String firstName, String middleName, String lastName, String biography, String url, String profileImageUrl) {
        u.setMiddleName(middleName);
        u.setLastName(lastName);
        u.setFirstName(firstName);
        u.setBiography(biography);
        u.setUrl(url);
        if (profileImageUrl != null && !profileImageUrl.equals("")) {
            if (u.getProfileImageUrl() != null && !u.getProfileImageUrl().equals("")) {
                //fileStorageService.deleteFile(u.getProfileImageUrl()); //TODO delete old profile pic
            }
            u.setProfileImageUrl(profileImageUrl);
        }
    }

    public void updateTwoFactorEmail(UserSecurity u, Boolean b) {
        u.setTwoFactorEmail(b);
    }

    public void updateEmailPreferences(UserSecurity u, EmailPreferencesEnum ep) {
        u.setEmailPreferences(ep);
    }

    public void updateProfilePrivacy(UserSecurity u, PrivacyEnum p) {
        u.setPrivacy(p);
    }

    public void updateInstance(UserSecurity u, String username) {
        u.setUsername(username);
    }

    public void initTwoFactorEmailToken(UserSecurity u) {
        u.setTwoFactorEmailCode(Utils.randomNumber(6));
        u.setTwoFactorEmailCreatedAt(new Timestamp(System.currentTimeMillis()));
        log.info("The two-factor code for the user " + u.getUsername() + " is " + u.getTwoFactorEmailCode());
    }

    public void confirmTwoFactorEmailToken(UserSecurity u) {
        u.setTwoFactorEmailCode(null);
        u.setTwoFactorEmailCreatedAt(null);
    }

    public void initPasswordToken(UserSecurity u) {
        String token;
        String hashedToken;
        do {
            token = Utils.generateNewToken(48);
            hashedToken = Utils.hash256(token);
        } while (userSecurityRepository.existsByPasswordResetToken(hashedToken));
        u.setPasswordResetToken(hashedToken);
        u.setPasswordResetSet(new Timestamp(System.currentTimeMillis()));
        u.setPasswordResetAt(null);
        log.info("The reset token for the user password is " + token
                + ", the link is: http://localhost:4200/reset/password/" + token);
    }

    public void confirmPasswordToken(UserSecurity u) {
        u.setPasswordResetToken(null);
        u.setPasswordResetSet(null);
        u.setPasswordResetAt(new Timestamp(System.currentTimeMillis()));
    }

}
