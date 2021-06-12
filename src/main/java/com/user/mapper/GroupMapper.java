package com.user.mapper;

import com.user.dto.GroupDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Group;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class GroupMapper extends AbstractMapper<GroupDto, Group> {

    @Override
    public GroupDto getDto(Group e) {
        GroupDto dto = super.getDto(e);
        dto.setName(e.getName());
        dto.setActive(e.isActive());
        dto.setOrder(e.getOrder());
        return dto;
    }

}