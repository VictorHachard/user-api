package com.user.service;

import com.user.dto.SettingDto;
import com.user.model.entities.Setting;
import com.user.model.repositories.SettingRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.SettingValidator;
import com.user.validator.UpdateSettingActiveValidator;
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
public class SettingService extends AbstractService<Setting, SettingRepository> {

    @Override
    public void create(AbstractValidator abstractValidator) {
        SettingValidator validator = (SettingValidator) abstractValidator;
        this.create(validator.getName());
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }

    public Setting create(String name) {
        Setting t = settingFacade.newInstance(
                name,
                false);
        this.getRepository().save(t);
        return t;
    }

    public List<SettingDto> getAllActiveDto() {
        List<Setting> settingList = this.getAll();
        settingList.removeIf(p -> !p.isActive());
        return this.getMapper().getAllDto(settingList);
    }

    public void updateActive(long id, UpdateSettingActiveValidator validator) {
        Setting t = this.get(id);
        t.setActive(validator.getActive());
        this.getRepository().save(t);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success theme active");
    }

}
