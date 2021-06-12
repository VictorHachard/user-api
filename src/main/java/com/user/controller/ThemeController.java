package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.ThemeDto;
import com.user.model.entities.Theme;
import com.user.validator.ThemeValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/theme/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class ThemeController extends AbstractController<Theme, ThemeDto> {

    @PostMapping("/create")
    public void create(@Valid @RequestBody ThemeValidator validator) {
        this.getService().create(validator);
    }

}
