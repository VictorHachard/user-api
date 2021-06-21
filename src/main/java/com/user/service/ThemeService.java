package com.user.service;

import com.user.model.entities.Theme;
import com.user.model.repositories.ThemeRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.ThemeValidator;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class ThemeService extends AbstractService<Theme, ThemeRepository> {

    @Override
    public void create(AbstractValidator abstractValidator) {
        ThemeValidator validator = (ThemeValidator) abstractValidator;
        this.create(validator.getName(), validator.getIsActive(), validator.getImageUrl());
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }

    public Theme create(String password, Boolean active, String imageUrl) {
        Theme t = themeFacade.newInstance(password, active, Math.toIntExact(this.count()), imageUrl);
        this.getRepository().save(t);
        return t;
    }

    public void update(long id, AbstractValidator abstractValidator) {
        ThemeValidator validator = (ThemeValidator) abstractValidator;
        Theme t = this.get(id);
        this.update(t, validator.getName(), validator.getIsActive(), Math.toIntExact(this.count()));
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " updated");
    }

    public void update(Theme t, String password, Boolean active, Integer order) {
        //TODO check if order Math.toIntExact(this.count())
        themeFacade.updateInstance(t, password, active);
        this.getRepository().save(t);
    }

}
