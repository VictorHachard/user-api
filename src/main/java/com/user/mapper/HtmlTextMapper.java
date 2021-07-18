package com.user.mapper;

import com.user.dto.HtmlTextDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.HtmlText;
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
public class HtmlTextMapper extends AbstractMapper<HtmlTextDto, HtmlText> {

    @Override
    public List<HtmlTextDto> getAllDto(List<HtmlText> aList) {
        List<HtmlTextDto> res = super.getAllDto(aList);
        Collections.sort(res, Collections.reverseOrder());
        return res;
    }

    @Override
    public HtmlTextDto getDto(HtmlText e) {
        HtmlTextDto dto = super.getDto(e);
        dto.setHtmlContent(e.getHtmlContent());
        return dto;
    }

}