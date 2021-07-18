package com.user.mapper;

import com.user.dto.HtmlTextDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.HtmlText;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class HtmlTextMapper extends AbstractMapper<HtmlTextDto, HtmlText> {

    @Override
    public HtmlTextDto getDto(HtmlText e) {
        HtmlTextDto dto = super.getDto(e);
        dto.setHtmlContent(e.getHtmlContent());
        return dto;
    }

}