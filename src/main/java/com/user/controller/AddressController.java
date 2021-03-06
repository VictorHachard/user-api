package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.AddressDto;
import com.user.interceptor.Authorisation;
import com.user.interceptor.AuthorisationForOverride;
import com.user.interceptor.AuthorisationForOverrideColumn;
import com.user.model.entities.Address;
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
@RequestMapping("api/v1/address/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
// Authorisation
@AuthorisationForOverrideColumn(table = {
        @AuthorisationForOverride(name = "count", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
        @AuthorisationForOverride(name = "create", roles = {RoleEnum.ROLE_USER}),
        @AuthorisationForOverride(name = "update", roles = {RoleEnum.ROLE_USER}),
        @AuthorisationForOverride(name = "delete", roles = {RoleEnum.ROLE_USER}),
        @AuthorisationForOverride(name = "getDto", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
        @AuthorisationForOverride(name = "getAllDto", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
        @AuthorisationForOverride(name = "get", roles = {RoleEnum.ROLE_ADMINISTRATOR}),
        @AuthorisationForOverride(name = "getAll", roles = {RoleEnum.ROLE_ADMINISTRATOR})
})
public class AddressController extends AbstractController<Address, AddressDto> {

}
