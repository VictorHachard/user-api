package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.SettingDto;
import com.user.dto.SettingSimplifiedDto;
import com.user.interceptor.Authorisation;
import com.user.interceptor.AuthorisationForOverride;
import com.user.interceptor.AuthorisationForOverrideColumn;
import com.user.model.entities.Setting;
import com.user.model.entities.enums.RoleEnum;
import com.user.service.SettingService;
import com.user.validator.SettingValidator;
import com.user.validator.UpdateSettingActiveValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/setting/")
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
        @AuthorisationForOverride(name = "getAllDto", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "get", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "getAll", roles = {RoleEnum.ROLE_OWNER})
})
public class SettingController extends AbstractController<Setting, SettingDto> {

    //TODO add a filter for setting and block if there is not in the list of active setting

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @GetMapping("dto/active")
    public List<SettingSimplifiedDto> getAllActiveDto() {
        SettingService service = (SettingService) this.getService();
        return service.getAllActiveDto();
    }

    @Authorisation(roles = {RoleEnum.ROLE_OWNER})
    @PostMapping("update/active/{id}")
    public void updateActive(@Valid @RequestBody UpdateSettingActiveValidator validator, @PathVariable("id") long id) {
        SettingService service = (SettingService) this.getService();
        service.updateActive(id, validator);
    }

}
