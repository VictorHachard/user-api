package com.user.mapper;

import com.user.dto.HtmlTextHistoryDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.HtmlTextHistory;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class HtmlTextHistoryMapper extends AbstractMapper<HtmlTextHistoryDto, HtmlTextHistory> {

    @Override
    public HtmlTextHistoryDto getDto(HtmlTextHistory e) {
        HtmlTextHistoryDto dto = super.getDto(e);
        dto.setHtmlHistory(htmlTextMapper.getAllDto(new ArrayList<>(e.getHtmlHistory())));
        return dto;
    }

}