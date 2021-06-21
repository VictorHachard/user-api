package com.user.mapper;

import com.user.dto.ImageDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Image;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class ImageMapper extends AbstractMapper<ImageDto, Image> {

    @Override
    public ImageDto getDto(Image e) {
        ImageDto dto = super.getDto(e);
        dto.setUrl(e.getUrl());
        return dto;
    }

}