package com.user.mapper;

import com.user.dto.UserSecurityDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.UserSecurity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class UserSecurityMapper extends AbstractMapper<UserSecurityDto, UserSecurity> {

    @Override
    public UserSecurityDto getDto(UserSecurity e) {
        UserSecurityDto dto = super.getDto(e);
        return dto;
    }

}
