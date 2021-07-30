package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.GroupDto;
import com.user.model.entities.Group;
import com.user.service.EmailService;
import com.user.validator.EmailValidator;
import com.user.validator.UpdateEmailBackupValidator;
import com.user.validator.UpdateEmailPreferencesValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/email/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class EmailController extends AbstractController<Group, GroupDto> {

    @PostMapping("create")
    public void create(@Valid @RequestBody EmailValidator validator) {
        this.getService().create(validator);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        this.getService().delete(id);
    }

    @PutMapping("priority")
    public void updatePriority(@Valid @RequestBody EmailValidator validator) {
        EmailService emailService = (EmailService) this.getService();
        emailService.updatePriority(validator);
    }

    @PutMapping("backup/{emailId}")
    public void updateBackup(@Valid @RequestBody UpdateEmailBackupValidator validator, @PathVariable("emailId") long emailId) {
        EmailService emailService = (EmailService) this.getService();
        emailService.updateBackup(emailId, validator);
    }

    @PutMapping("preferences")
    public void updatePreferences(@Valid @RequestBody UpdateEmailPreferencesValidator validator) {
        EmailService emailService = (EmailService) this.getService();
        emailService.updatePreferences(validator);
    }

    @PostMapping("action/confirm")
    public void actionConfirmEmail(@Valid @RequestBody String token) {
        EmailService emailService = (EmailService) this.getService();
        emailService.confirmEmail(token);
    }

    @PostMapping("action/confirm/resend/{emailId}")
    public void actionConfirmResendEmail(@PathVariable("emailId") long emailId) {
        EmailService emailService = (EmailService) this.getService();
        emailService.resendConfirmEmail(emailId);
    }

}
