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
import java.util.Collections;
import java.util.List;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class ThemeMapper extends AbstractMapper<ThemeDto, Theme> {

    @Override
    public List<ThemeDto> getAllDto(List<Theme> aList) {
        List<ThemeDto> res = super.getAllDto(aList);
        Collections.sort(res);
        return res;
    }

    @Override
    public ThemeDto getDto(Theme e) {
        ThemeDto dto = super.getDto(e);
        dto.setName(e.getName());
        dto.setActive(e.isActive());
        dto.setOrder(e.getOrder());
        dto.setImage(e.getImageUrl());
        dto.setPrimaryColor(e.getPrimaryColor());
        dto.setSecondaryColor(e.getSecondaryColor());
        dto.setTertiaryColor(e.getTertiaryColor());
        dto.setQuaternaryColor(e.getQuaternaryColor());
        dto.setPrimaryTextColor(e.getPrimaryTextColor());
        dto.setSecondaryTextColor(e.getSecondaryTextColor());
        return dto;
    }

    public List<ThemeSimplifiedDto> getAllSimplifiedDto(List<Theme> aList) {
        List<ThemeSimplifiedDto> res = new ArrayList<>();
        aList.forEach(a -> {
            res.add(this.getSimplifiedDto(a));
        });
        Collections.sort(res);
        return res;
    }

    public ThemeSimplifiedDto getSimplifiedDto(Theme e) {
        ThemeSimplifiedDto dto = new ThemeSimplifiedDto();
        dto.setId(e.getId());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setName(e.getName());
        dto.setImage(e.getImageUrl());
        dto.setPrimaryColor(e.getPrimaryColor());
        dto.setSecondaryColor(e.getSecondaryColor());
        dto.setTertiaryColor(e.getTertiaryColor());
        dto.setQuaternaryColor(e.getQuaternaryColor());
        dto.setPrimaryTextColor(e.getPrimaryTextColor());
        dto.setSecondaryTextColor(e.getSecondaryTextColor());
        return dto;
    }

}
