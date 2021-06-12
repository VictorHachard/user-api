package com.user.service;

import com.user.dto.UserSecurityDto;
import com.user.model.entities.Email;
import com.user.model.entities.Password;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.repositories.UserSecurityRepository;
import com.user.service.commons.AbstractService;
import com.user.utils.Utils;
import com.user.validator.EmailValidator;
import com.user.validator.LoginFromCookieValidator;
import com.user.validator.UserSecurityValidator;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class UserSecurityService extends AbstractService<UserSecurity, UserSecurityRepository> {

    @Override
    public void create(AbstractValidator abstractValidator) {
        UserSecurityValidator validator = (UserSecurityValidator) abstractValidator;
        this.create(validator.getUsername(), validator.getEmail(), validator.getPassword());
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }

    public UserSecurity create(String username, String email, String password) {
        String emailLower = email.toLowerCase();
        //boolean existsByEmail = this.getRepository().existsByEmail(emailLower);
        boolean existsByEmailPriority = emailRepository.existsByEmailAndPriority(emailLower, PriorityEnum.PRINCIPAL);
        boolean existsByUsername = this.getRepository().existsByUsername(username);
        if (existsByUsername) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This username is already in the database");
        } else if (existsByEmailPriority) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This email is already define in the database as PRINCIPAL");
        }
        Email e = emailService.create(emailLower, PriorityEnum.PRINCIPAL);
        Password p = passwordService.create(password);

        UserSecurity user = userSecurityFacade.newInstance(e, p, username);
        this.getRepository().save(user);
        return user;
    }

    public UserSecurityDto login(String username, String password) {
        if (!this.getRepository().existsByUsername(username)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The username is not correct");
        }
        UserSecurity user = this.getRepository().findByUsername(username).get();
        List<Password> passwordList = new ArrayList<>(user.getPasswordList());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, passwordList.get(passwordList.size() -1).getPassword())) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The password is not correct");
        }
        this.setAuthToken(user);
        return (UserSecurityDto) this.getMapper().getDto(user);
    }

    public UserSecurityDto connectFromCookie(LoginFromCookieValidator validator) {
        if (!this.getRepository().existsByCookieRemember(validator.getToken())) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The token is not correct");
        }

        UserSecurity user = this.getRepository().findByCookieRemember(validator.getToken()).get();
        //TODO check if token not expired
        this.setAuthToken(user);
        return (UserSecurityDto) this.getMapper().getDto(user);
    }

    private void setAuthToken(UserSecurity u) {
        String token;
        do { token = Utils.generateNewToken(48); } while (cookieRememberRepository.existsByToken(token));
        u.setAuthToken(token);
        u.setAuthTokenCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.getRepository().save(u);
    }

    public void logOut() {

    }

    public void addEmail(EmailValidator validator) {
        if (PriorityEnum.valueOf(validator.getPriorityEnum()) == PriorityEnum.PRINCIPAL) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "You cannot define a new PRINCIPAL email, only one PRINCIPAL email");
        }
        Email e = emailService.create(validator.getEmail().toLowerCase(), PriorityEnum.SECONDARY);
        UserSecurity u = this.getUser();
        u.addEmail(e);
        this.getRepository().save(u);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success new email added");
    }

    public void removeEmail(long id) {
        //TODO remove from user
        emailService.delete(id);
    }

    /*public boolean checkToken(Users user, String token) {
        return user.getToken() != null
                && user.getToken().equals(token)
                && Date.from(Instant.now().minusSeconds(86400)).after(user.getTokenCreatedAt());
    }

    public void setToken(Users user) {
        user.setToken(Utils.generateNewToken(60));
        user.setTokenCreatedAt(new Timestamp(System.currentTimeMillis()));
    }*/

}
