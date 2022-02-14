package com.user.mapper;

import com.user.dto.SessionDto;
import com.user.dto.SessionSimplifiedDto;
import com.user.dto.UserSecurityProfileDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Session;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.PrivacyEnum;
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
public class SessionMapper extends AbstractMapper<SessionDto, Session> {

    /*
    Only called for the actual session
     */
    @Override
    public SessionDto getDto(Session e) {
        SessionDto dto = super.getDto(e);
        dto.setToken(e.getToken());
        dto.setIp(e.getIp());
        dto.setOnMobile(e.getOnMobile());
        dto.setPlatform(e.getPlatform());
        dto.setUserAgent(e.getUserAgent());
        dto.setLastConnection(e.getLastConnection());
        return dto;
    }

    /*
    Do some weird stuff with this one. If I remember I will write a proper documentation.
     */
    public List<SessionDto> getAllSimplifiedDto(List<Session> aList) {
        List<SessionDto> res = super.getAllDto(aList);
        for (SessionDto dto : res) {
            dto.setToken(null);
        }
        Collections.sort(res);
        return res;
    }

    public SessionSimplifiedDto getSimplifiedDto(Session s) {
        SessionSimplifiedDto dto = new SessionSimplifiedDto();
        dto.setIp(s.getIp());
        dto.setPlatform(s.getPlatform());
        dto.setUserAgent(s.getUserAgent());
        dto.setLastConnection(s.getLastConnection());
        return dto;
    }

}
