package com.user.service;

import com.user.model.entities.Password;
import com.user.model.repositories.PasswordRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.PasswordValidator;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class PasswordService extends AbstractService<Password, PasswordRepository> {

    @Override
    public void create(AbstractValidator abstractValidator) {
        PasswordValidator validator = (PasswordValidator) abstractValidator;
        this.create(validator.getPassword());
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }

    public Password create(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Password e = passwordFacade.newInstance(passwordEncoder.encode(password));
        this.getRepository().save(e);
        return e;
    }

}
