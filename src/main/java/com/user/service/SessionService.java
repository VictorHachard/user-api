package com.user.service;

import com.user.model.entities.Email;
import com.user.model.entities.Session;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.SecurityLogEnum;
import com.user.model.repositories.SessionRepository;
import com.user.service.commons.AbstractService;
import com.user.utils.Utils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class SessionService extends AbstractService<Session, SessionRepository> {

    public Session create() {
        Session s = sessionFacade.newInstance();
        this.getRepository().save(s);
        return s;
    }

    public void delete(Session s) {
        UserSecurity u = userSecurityRepository.findBySession(s).get();
        u.getSessionList().remove(s);
        userSecurityRepository.save(u);
        this.getRepository().delete(s);
    }

    public String setAuthToken(Session s) {
        Date currentDate = new Date();
        //if (u.getAuthToken() == null || u.getAuthToken().equals("") || u.getAuthTokenCreatedAt() == null /*|| u.getAuthTokenCreatedAt().before(new Date(currentDate.getTime() - 1l * 24 * 60 * 60 * 1000))*/) {
        String token = Utils.generateNewToken(48);
        String hashedToken = Utils.hash256(token);
        s.setAuthToken(hashedToken);
        s.setAuthTokenCreatedAt(new Timestamp(System.currentTimeMillis()));
        //}
        this.getRepository().save(s);
        return token;
    }

    public void deleteToken(Session s) {
        s.setAuthToken(null);
        s.setAuthTokenCreatedAt(null);
        this.getRepository().save(s);
    }

}
