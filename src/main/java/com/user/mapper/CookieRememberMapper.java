package com.user.mapper;

import com.user.dto.CookieRememberDto;
import com.user.dto.EmailDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.CookieRemember;
import com.user.model.entities.Email;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class CookieRememberMapper extends AbstractMapper<CookieRememberDto, CookieRemember> {

    @Override
    public CookieRememberDto getDto(CookieRemember e) {
        CookieRememberDto dto = super.getDto(e);
        dto.setToken(e.getToken());
        return dto;
    }

}
