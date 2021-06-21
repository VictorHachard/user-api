package com.user.mapper;

import com.user.dto.UserSecurityDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.UserSecurity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class UserSecurityMapper extends AbstractMapper<UserSecurityDto, UserSecurity> {

    @Override
    public UserSecurityDto getDto(UserSecurity e) {
        UserSecurityDto dto = super.getDto(e);
        dto.setUsername(e.getUsername());
        String name = e.getFirstName() != null ? e.getFirstName() + " " : "";
        name += e.getMiddleName() != null ? e.getMiddleName() + " " : "";
        name += e.getLastName() != null ? e.getLastName()  : "";
        dto.setNameFormatted(name);
        dto.setAuthToken(e.getAuthToken());
        dto.setEmailList(emailMapper.getAllDto(new ArrayList<>(e.getEmailList())));
        dto.setGroupDtoList(groupMapper.getAllDto(new ArrayList<>(e.getGroupList())));
        dto.setRoleDtoList(roleMapper.getAllDto(new ArrayList<>(e.getPermissionList())));
        dto.setPrivacy(e.getPrivacy().name());
        dto.setThemeSimplifiedDto(e.getTheme() != null ? themeMapper.getSimplifiedDto(e.getTheme()) : null);
        dto.setBiography(e.getBiography());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setMiddleName(e.getMiddleName());
        dto.setBirth(e.getBirth());
        dto.setUrl(e.getUrl());
        return dto;
    }

}
