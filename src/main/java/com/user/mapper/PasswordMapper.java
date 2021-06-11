package com.user.mapper;

import com.user.dto.PasswordDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Password;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class PasswordMapper extends AbstractMapper<PasswordDto, Password> {

    @Override
    public PasswordDto getDto(Password e) {
        PasswordDto dto = super.getDto(e);
        return dto;
    }

}
