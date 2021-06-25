package com.user.service;

import com.user.dto.ThemeDto;
import com.user.model.entities.Theme;
import com.user.model.repositories.ThemeRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.ThemeValidator;
import com.user.validator.UpdateThemeActiveValidator;
import com.user.validator.UpdateThemeValidator;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class ThemeService extends AbstractService<Theme, ThemeRepository> {

    @Override
    public void create(AbstractValidator abstractValidator) {
        ThemeValidator validator = (ThemeValidator) abstractValidator;
        this.create(validator.getName(),
                validator.getImageUrl(),
                validator.getPrimaryColor(),
                validator.getSecondaryColor(),
                validator.getTertiaryColor(),
                validator.getQuaternaryColor(),
                validator.getPrimaryTextColor(),
                validator.getSecondaryTextColor());
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }

    public Theme create(String password,
                        String imageUrl,
                        String primaryColor,
                        String secondaryColor,
                        String tertiaryColor,
                        String quaternaryColor,
                        String primaryTextColor,
                        String secondaryTextColor) {
        Theme t = themeFacade.newInstance(
                password,
                false,
                Math.toIntExact(this.count()),
                imageUrl,
                primaryColor,
                secondaryColor,
                tertiaryColor,
                quaternaryColor,
                primaryTextColor,
                secondaryTextColor);
        this.getRepository().save(t);
        return t;
    }

    public List<ThemeDto> getAllActiveDto() {
        List<Theme> themeList = this.getAll();
        themeList.removeIf(p -> !p.isActive());
        return this.getMapper().getAllDto(themeList);
    }

    public void updateActive(long id, UpdateThemeActiveValidator validator) {
        Theme t = this.get(id);
        t.setActive(validator.getActive());
        this.getRepository().save(t);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success theme active");
    }

    public void updateT(long id, UpdateThemeValidator validator) {
        Theme t = this.get(id);
        themeFacade.updateInstance(
                t,
                validator.getName(),
                validator.getPrimaryColor(),
                validator.getSecondaryColor(),
                validator.getTertiaryColor(),
                validator.getQuaternaryColor(),
                validator.getPrimaryTextColor(),
                validator.getSecondaryTextColor());
        this.getRepository().save(t);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success theme update");
    }

    /*public void update(long id, AbstractValidator abstractValidator) {
        ThemeValidator validator = (ThemeValidator) abstractValidator;
        Theme t = this.get(id);
        this.update(t, validator.getName(), validator.getIsActive(), Math.toIntExact(this.count()));
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " updated");
    }*/

    public void update(Theme t, String password, Boolean active, Integer order) {
        //TODO check if order Math.toIntExact(this.count())
        themeFacade.updateInstance(t, password, active);
        this.getRepository().save(t);
    }

}
