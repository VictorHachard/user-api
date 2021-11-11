package com.user.controller;

import com.user.Authorisation;
import com.user.controller.commons.AbstractController;
import com.user.dto.GroupDto;
import com.user.model.entities.Group;
import com.user.model.entities.enums.RoleEnum;
import com.user.validator.AddressValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/address/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class AddressController extends AbstractController<Group, GroupDto> {

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("create")
    public void create(@Valid @RequestBody AddressValidator validator) {
        this.getService().create(validator);
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        this.getService().delete(id);
    }

}
