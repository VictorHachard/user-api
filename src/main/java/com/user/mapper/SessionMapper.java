package com.user.mapper;

import com.user.dto.SessionDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Session;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class SessionMapper extends AbstractMapper<SessionDto, Session> {

    @Override
    public SessionDto getDto(Session e) {
        SessionDto dto = super.getDto(e);
        dto.setToken(e.getToken());
        return dto;
    }

}
