package com.user.mapper;

import com.user.dto.RoleDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Role;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class RoleMapper extends AbstractMapper<RoleDto, Role> {

    @Override
    public RoleDto getDto(Role e) {
        RoleDto dto = super.getDto(e);
        return dto;
    }

}
