package com.user.mapper;

import com.user.dto.SettingDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Setting;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class SettingMapper extends AbstractMapper<SettingDto, Setting> {

    @Override
    public SettingDto getDto(Setting e) {
        SettingDto dto = super.getDto(e);
        dto.setName(e.getName());
        dto.setActive(e.getActive());
        dto.setCanUpdate(e.getCanUpdate());
        return dto;
    }

}
