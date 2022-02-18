package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.CountryDto;
import com.user.interceptor.Authorisation;
import com.user.interceptor.AuthorisationForOverride;
import com.user.interceptor.AuthorisationForOverrideColumn;
import com.user.model.entities.Country;
import com.user.model.entities.enums.RoleEnum;
import com.user.service.CountryService;
import com.user.validator.AddressValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/country/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
// Authorisation
@AuthorisationForOverrideColumn(table = {
        @AuthorisationForOverride(name = "count", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "create", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "update", roles = {RoleEnum.ROLE_OWNER}), // Block
        @AuthorisationForOverride(name = "delete", roles = {RoleEnum.ROLE_USER}),
        @AuthorisationForOverride(name = "getDto", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "getAllDto", roles = {RoleEnum.ROLE_USER}),
        @AuthorisationForOverride(name = "get", roles = {RoleEnum.ROLE_OWNER}),
        @AuthorisationForOverride(name = "getAll", roles = {RoleEnum.ROLE_OWNER})
})
public class CountryController extends AbstractController<Country, CountryDto> {

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @GetMapping("dto/active")
    public List<CountryDto> getAllActiveDto(@RequestParam(defaultValue = "0") Integer pageIndex,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(defaultValue = "id") String sortBy,
                                            @RequestParam(defaultValue = "asc") String orderBy,
                                            @RequestParam(defaultValue = "null") String searchBy,
                                            @RequestParam(defaultValue = "null") String searchValue) {
        CountryService service = (CountryService) this.getService();
        return service.getAllActiveDto(pageIndex, pageSize, sortBy, orderBy, searchBy, searchValue);
    }

}
