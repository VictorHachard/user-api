package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.ThemeDto;
import com.user.model.entities.Theme;
import com.user.service.ThemeService;
import com.user.service.UserSecurityService;
import com.user.validator.ThemeValidator;
import com.user.validator.UpdateEmailBackupValidator;
import com.user.validator.UpdateThemeActiveValidator;
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
public class ThemeController extends AbstractController<Theme, ThemeDto> {

    @PostMapping("/create")
    public void create(@Valid @RequestBody ThemeValidator validator) {
        this.getService().create(validator);
    }

    @GetMapping("/dto/appearance")
    public List<ThemeDto> getAppearanceAllDto() {
        ThemeService service = (ThemeService) this.getService();
        return service.getAppearanceAllDto();
    }

    @PostMapping("update/active/{id}")
    public void updateActive(@Valid @RequestBody UpdateThemeActiveValidator validator, @PathVariable("id") long id) {
        ThemeService service = (ThemeService) this.getService();
        service.updateActive(id, validator);
    }

}
