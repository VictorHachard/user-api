package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.HtmlTextHistoryDto;
import com.user.interceptor.Authorisation;
import com.user.interceptor.AuthorisationForOverride;
import com.user.interceptor.AuthorisationForOverrideColumn;
import com.user.model.entities.HtmlTextHistory;
import com.user.model.entities.enums.RoleEnum;
import com.user.service.HtmlTextHistoryService;
import com.user.validator.HtmlTextValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/html-text-history/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
// Authorisation
@AuthorisationForOverrideColumn(table = {
        @AuthorisationForOverride(name = "count", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "create", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "update", roles = {RoleEnum.ROLE_OWNER}), // Block
        @AuthorisationForOverride(name = "delete", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "getDto", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "getAllDto", roles = {RoleEnum.ROLE_USER}), // Block
        @AuthorisationForOverride(name = "get", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "getAll", roles = {RoleEnum.ROLE_OWNER})
})
public class HtmlTextHistoryController extends AbstractController<HtmlTextHistory, HtmlTextHistoryDto> {

    @Authorisation(roles = {RoleEnum.ROLE_OWNER})
    @PostMapping("add/{id}")
    public void add(@Valid @RequestBody HtmlTextValidator validator, @PathVariable("id") long id) {
        HtmlTextHistoryService service = (HtmlTextHistoryService) this.getService();
        service.add(validator, id);
    }

}
