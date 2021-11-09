package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.SettingDto;
import com.user.dto.ThemeDto;
import com.user.model.entities.Setting;
import com.user.model.entities.Theme;
import com.user.service.SettingService;
import com.user.service.ThemeService;
import com.user.validator.*;
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
public class SettingController extends AbstractController<Setting, SettingDto> {

    @PostMapping("create")
    public void create(@Valid @RequestBody SettingValidator validator) {
        this.getService().create(validator);
    }

    @GetMapping("dto/active")
    public List<SettingDto> getAllActiveDto() {
        SettingService service = (SettingService) this.getService();
        return service.getAllActiveDto();
    }

    @PostMapping("update/active/{id}")
    public void updateActive(@Valid @RequestBody UpdateSettingActiveValidator validator, @PathVariable("id") long id) {
        SettingService service = (SettingService) this.getService();
        service.updateActive(id, validator);
    }

//    @PostMapping("update/{id}")
//    public void updateActive(@Valid @RequestBody UpdateThemeValidator validator, @PathVariable("id") long id) {
//        SettingService service = (SettingService) this.getService();
//        service.updateT(id, validator);
//    }

}
