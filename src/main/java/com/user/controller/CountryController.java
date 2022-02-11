package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.CountryDto;
import com.user.dto.GroupDto;
import com.user.interceptor.Authorisation;
import com.user.interceptor.AuthorisationForOverride;
import com.user.interceptor.AuthorisationForOverrideColumn;
import com.user.model.entities.Country;
import com.user.model.entities.Group;
import com.user.model.entities.enums.RoleEnum;
import com.user.validator.AddressValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/country/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
// Authorisation
@AuthorisationForOverrideColumn(table = {
        @AuthorisationForOverride(name = "count", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "delete", roles = {RoleEnum.ROLE_USER}),
        @AuthorisationForOverride(name = "getDto", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "getAllDto", roles = {RoleEnum.ROLE_USER}),
        @AuthorisationForOverride(name = "get", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "getAll", roles = {RoleEnum.ROLE_OWNER})
})
public class CountryController extends AbstractController<Country, CountryDto> {

    @Authorisation(roles = {RoleEnum.ROLE_OWNER})
    @PostMapping("create")
    public void create(@Valid @RequestBody AddressValidator validator) {
        this.getService().create(validator);
    }

}
