package com.user.mapper;

import com.user.dto.SecurityLogDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.SecurityLog;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class SecurityLogMapper extends AbstractMapper<SecurityLogDto, SecurityLog> {

    @Override
    public SecurityLogDto getDto(SecurityLog e) {
        SecurityLogDto dto = super.getDto(e);
        dto.setSecurityLog(e.getSecurityLog().name().replace('_', ' '));
        dto.setInfo(e.getInfo());
        dto.setSessionSimplifiedDto(sessionMapper.getSimplifiedDto(e.getSession()));
        return dto;
    }

}
