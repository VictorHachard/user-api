package com.user.mapper;

import com.user.dto.ThemeDto;
import com.user.dto.ThemeSimplifiedDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Theme;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class ThemeMapper extends AbstractMapper<ThemeDto, Theme> {

    @Override
    public ThemeDto getDto(Theme e) {
        ThemeDto dto = super.getDto(e);
        dto.setName(e.getName());
        dto.setActive(e.isActive());
        dto.setOrder(e.getOrder());
        return dto;
    }

    public List<ThemeSimplifiedDto> getAllSimplifiedDto(List<Theme> aList) {
        List<ThemeSimplifiedDto> res = new ArrayList<>();
        aList.forEach(a -> {
            res.add(this.getSimplifiedDto(a));
        });
        return res;
    }

    public ThemeSimplifiedDto getSimplifiedDto(Theme e) {
        ThemeSimplifiedDto dto = (ThemeSimplifiedDto) super.getAbstractDto(e);
        dto.setName(e.getName());
        return dto;
    }

}
