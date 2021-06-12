package com.user.service;

import com.user.model.entities.Email;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.PrivacyEnum;
import com.user.model.repositories.EmailRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.EmailValidator;
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
public class EmailService extends AbstractService<Email, EmailRepository> {

    @Override
    public void create(AbstractValidator abstractValidator) {
        EmailValidator validator = (EmailValidator) abstractValidator;
        this.create(validator.getEmail(), PriorityEnum.valueOf(validator.getPriorityEnum())); //TODO check value
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }

    public Email create(String email, PriorityEnum pe) {
        if (this.getRepository().existsByEmail(email)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This email is already in the database");
        }
        Email e = emailFacade.newInstance(email, pe, PrivacyEnum.PRIVATE);
        emailFacade.initToken(e);
        this.getRepository().save(e);
        return e;
    }

    @Override
    public void delete(long id) {
        if (!this.getRepository().existsById(id)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        } else if (this.getRepository().findById(id).get().getPriority() == PriorityEnum.PRINCIPAL) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "Cannot delete PRINCIPAL email");
        } else {
            this.getRepository().deleteById(id);
        }
    }

}
