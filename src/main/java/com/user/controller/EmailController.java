package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.GroupDto;
import com.user.interceptor.Authorisation;
import com.user.interceptor.AuthorisationForOverride;
import com.user.interceptor.AuthorisationForOverrideColumn;
import com.user.model.entities.Group;
import com.user.model.entities.enums.RoleEnum;
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
// Authorisation
@AuthorisationForOverrideColumn(table = {
        @AuthorisationForOverride(name = "count", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
        @AuthorisationForOverride(name = "delete", roles = {RoleEnum.ROLE_USER}),
        @AuthorisationForOverride(name = "getDto", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
        @AuthorisationForOverride(name = "getAllDto", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
        @AuthorisationForOverride(name = "get", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
        @AuthorisationForOverride(name = "getAll", roles = {RoleEnum.ROLE_ADMINISTRATOR})
})
public class EmailController extends AbstractController<Group, GroupDto> {

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("create")
    public void create(@Valid @RequestBody EmailValidator validator) {
        this.getService().create(validator);
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PutMapping("priority")
    public void updatePriority(@Valid @RequestBody EmailValidator validator) {
        EmailService emailService = (EmailService) this.getService();
        emailService.updatePriority(validator);
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PutMapping("backup/{emailId}")
    public void updateBackup(@Valid @RequestBody UpdateEmailBackupValidator validator, @PathVariable("emailId") long emailId) {
        EmailService emailService = (EmailService) this.getService();
        emailService.updateBackup(emailId, validator);
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
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

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("action/confirm/resend/{emailId}")
    public void actionConfirmResendEmail(@PathVariable("emailId") long emailId) {
        EmailService emailService = (EmailService) this.getService();
        emailService.resendConfirmEmail(emailId);
    }

}
