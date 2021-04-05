package com.user.controller;

import com.user.model.entities.Email;
import com.user.model.entities.Password;
import com.user.model.entities.PriorityEnum;
import com.user.model.entities.UserSecurity;
import com.user.model.repositories.EmailRepository;
import com.user.model.repositories.PasswordRepository;
import com.user.model.repositories.UserSecurityRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("api/v1/user/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class UserSecurityController {

    @Autowired
    UserSecurityRepository userSecurityRepository;

    @Autowired
    PasswordRepository passwordRepository;

    @Autowired
    EmailRepository emailRepository;

    @PostMapping("/create")
    public void create(@Valid @RequestBody UserSecurityValidator validator) {
        boolean existsByEmail = userSecurityRepository.existsByEmail(validator.getEmail());
        boolean existsByUsername = userSecurityRepository.existsByUsername(validator.getUsername());
        if (existsByEmail && existsByUsername) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email and this username are already in the database");
        } else if (existsByEmail) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already in the database");
        } else if (existsByUsername) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This username is already in the database");
        } else {
            UserSecurity user = new UserSecurity();
            Email email = new Email();

            email.setPriority(PriorityEnum.PRINCIPAL);
            email.setEmail(validator.getEmail());
            Password password = new Password();
            password.setPassword(validator.getPassword());
            user.addEmail(email);
            user.addPassword(password);
            user.setUsername(validator.getUsername());
            passwordRepository.save(password);
            emailRepository.save(email);
            userSecurityRepository.save(user);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Success user created");
        }
    }

    @GetMapping("/count")
    public long count() {
        return userSecurityRepository.count();
    }

    @GetMapping("/{id}")
    public UserSecurity get(@PathVariable("id") long id) {
        if (userSecurityRepository.existsById(id)) {
            return userSecurityRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user doesn't exist in the database");
        }
    }

    @GetMapping("")
    public List<UserSecurity> getAll() {
        if (userSecurityRepository.count() >= 1) {
            return userSecurityRepository.findAll();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no user in the database");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        if (userSecurityRepository.existsById(id)) {
            userSecurityRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Success user deleted");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user doesn't exist in the database");
        }
    }

}

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
class UserSecurityValidator {

    @NotNull(message = "")
    @NotEmpty
    String username;

    @NotNull(message = "")
    @NotEmpty
    @javax.validation.constraints.Email
    String email;

    @NotNull(message = "")
    @NotEmpty
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{6,}", message = "Password must contain at least one lowercase, uppercase, numeric, special character and between six and sixteen characters")
    String password;

}

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
class UserSecurityAddEmailValidator {

    @NotNull(message = "")
    @NotEmpty
    @javax.validation.constraints.Email
    String email;

}
