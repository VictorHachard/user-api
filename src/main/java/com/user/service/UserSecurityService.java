package com.user.service;

import com.user.dto.UserSecurityDto;
import com.user.model.entities.Email;
import com.user.model.entities.Password;
import com.user.model.entities.PriorityEnum;
import com.user.model.entities.UserSecurity;
import com.user.model.repositories.UserSecurityRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.UserSecurityValidator;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        boolean existsByEmail = this.getRepository().existsByEmail(email);
        boolean existsByUsername = this.getRepository().existsByUsername(username);
        if (existsByEmail && existsByUsername) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This email and this username are already in the database");
        } else if (existsByEmail) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This email is already in the database");
        } else if (existsByUsername) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This username is already in the database");
        }
        Email e = emailService.create(email, PriorityEnum.PRINCIPAL);
        Password p = passwordService.create(password);

        UserSecurity user = userSecurityFacade.newInstance(e, p, username);
        this.getRepository().save(user);
        return user;
    }

    public UserSecurityDto login(String username, String password) {
        boolean existsByUsername = this.getRepository().existsByUsername(username);

        if (!existsByUsername) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The username is not correct");
        }
        UserSecurity user = this.getRepository().findByUsername(username).get();
        List<Password> passwordList = new ArrayList<>(user.getPasswordList());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, passwordList.get(passwordList.size() -1).getPassword())) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "The password is not correct");
        }
        return (UserSecurityDto) this.getMapper().getDto(user);
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
