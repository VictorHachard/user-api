package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.ThemeDto;
import com.user.interceptor.Authorisation;
import com.user.interceptor.AuthorisationForOverride;
import com.user.interceptor.AuthorisationForOverrideColumn;
import com.user.model.entities.Theme;
import com.user.model.entities.enums.RoleEnum;
import com.user.service.ThemeService;
import com.user.validator.ThemeValidator;
import com.user.validator.UpdateThemeActiveValidator;
import com.user.validator.UpdateThemeValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/theme/")
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
public class ThemeController extends AbstractController<Theme, ThemeDto> {

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @GetMapping("dto/active")
    public List<ThemeDto> getAllActiveDto() {
        ThemeService service = (ThemeService) this.getService();
        return service.getAllActiveDto();
    }

    @Authorisation(roles = {RoleEnum.ROLE_OWNER})
    @PostMapping("update/active/{id}")
    public void updateActive(@Valid @RequestBody UpdateThemeActiveValidator validator, @PathVariable("id") long id) {
        ThemeService service = (ThemeService) this.getService();
        service.updateActive(id, validator);
    }

    @Authorisation(roles = {RoleEnum.ROLE_OWNER})
    @PostMapping("update/{id}")
    public void updateActive(@Valid @RequestBody UpdateThemeValidator validator, @PathVariable("id") long id) {
        ThemeService service = (ThemeService) this.getService();
        service.updateT(id, validator);
    }

}
