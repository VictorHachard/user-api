package com.user.service;

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
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class UserSecurityService extends AbstractService<UserSecurity, UserSecurityRepository> {

    @Override
    public void create(AbstractValidator abstractValidator) {
        UserSecurityValidator validator = (UserSecurityValidator) abstractValidator;

        this.create(validator.getUsername(), validator.getEmail(), validator.getPassword());
        this.responseStatus(HttpStatus.NO_CONTENT, "Success user created");
    }

    public UserSecurity create(String username, String email, String password) {
        boolean existsByEmail = this.getRepository().existsByEmail(username);
        boolean existsByUsername = this.getRepository().existsByUsername(email);
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

}
