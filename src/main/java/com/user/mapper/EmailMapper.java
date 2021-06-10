package com.user.mapper;

import com.user.dto.EmailDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Email;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class EmailMapper extends AbstractMapper<EmailDto, Email> {

    @Override
    public EmailDto getDto(Email e) {
        EmailDto dto = super.getDto(e);
        dto.setEmail(e.getEmail());
        return dto;
    }

}
