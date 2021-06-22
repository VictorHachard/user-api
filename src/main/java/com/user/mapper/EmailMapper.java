package com.user.mapper;

import com.user.dto.EmailDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Email;
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
public class EmailMapper extends AbstractMapper<EmailDto, Email> {

    public List<EmailDto> getAllDto(List<Email> aList) {
        List<EmailDto> res = new ArrayList<>();
        aList.forEach(a -> {
            res.add(this.getDto(a));
        });
        Collections.sort(res);
        return res;
    }

    @Override
    public EmailDto getDto(Email e) {
        EmailDto dto = super.getDto(e);
        dto.setEmail(e.getEmail());
        dto.setPriority(e.getPriority().name());
        dto.setConfirmed(e.getEmailConfirmed());
        dto.setPrivacy(e.getPrivacy().name());
        dto.setBackup(e.getBackup());
        return dto;
    }

}
