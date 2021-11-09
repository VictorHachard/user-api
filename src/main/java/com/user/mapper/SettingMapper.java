package com.user.mapper;

import com.user.dto.SettingDto;
import com.user.dto.ThemeDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Setting;
import com.user.model.entities.Theme;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class SettingMapper extends AbstractMapper<SettingDto, Setting> {

    @Override
    public List<SettingDto> getAllDto(List<Setting> aList) {
        List<SettingDto> res = super.getAllDto(aList);
        Collections.sort(res);
        return res;
    }

    @Override
    public SettingDto getDto(Setting e) {
        SettingDto dto = super.getDto(e);
        dto.setName(e.getName());
        dto.setActive(e.isActive());
        dto.setCanUpdate(e.isCanUpdate());
        return dto;
    }

}
