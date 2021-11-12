package com.user.mapper;

import com.user.dto.SessionDto;
import com.user.dto.ThemeDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Session;
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
public class SessionMapper extends AbstractMapper<SessionDto, Session> {

    /*
    Only called for the actual session
     */
    @Override
    public SessionDto getDto(Session e) {
        SessionDto dto = super.getDto(e);
        dto.setToken(e.getToken());
        dto.setIp(e.getIp());
        return dto;
    }

    public List<SessionDto> getAllSimplifiedDto(List<Session> aList) {
        List<SessionDto> res = super.getAllDto(aList);
        for (SessionDto dto : res) {
            dto.setToken(null);
        }
        Collections.sort(res);
        return res;
    }

}
