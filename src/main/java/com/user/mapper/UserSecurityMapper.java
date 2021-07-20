package com.user.mapper;

import com.user.dto.UserSecurityDto;
import com.user.dto.UserSecurityProfileDto;
import com.user.dto.UserSecuritySimplifiedDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.PrivacyEnum;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class UserSecurityMapper extends AbstractMapper<UserSecurityDto, UserSecurity> {

    @Override
    public UserSecurityDto getDto(UserSecurity e) {
        UserSecurityDto dto = super.getDto(e);
        dto.setUsername(e.getUsername());
        dto.setNameFormatted(this.getName(e));
        dto.setAuthToken(e.getAuthToken());
        dto.setEmailList(emailMapper.getAllDto(new ArrayList<>(e.getEmailList())));
        dto.setGroupDtoList(groupMapper.getAllDto(new ArrayList<>(e.getGroupList())));
        dto.setRoleDtoList(roleMapper.getAllDto(new ArrayList<>(e.getPermissionList())));
        dto.setBlockedUserDtoList(this.getAllUserSecuritySimplifiedDto(new ArrayList<>(e.getBlockedUserSecurity())));
        dto.setPrivacy(e.getPrivacy().name());
        dto.setThemeSimplifiedDto(e.getTheme() != null ? themeMapper.getSimplifiedDto(e.getTheme()) : null);
        dto.setBiography(e.getBiography());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setMiddleName(e.getMiddleName());
        dto.setBirth(e.getBirth());
        dto.setUrl(e.getUrl());
        dto.setProfileImage(e.getProfileImageUrl());
        dto.setEmailPreferences(e.getEmailPreferences().name());
        dto.setTwoFactorEmail(e.getTwoFactorEmail());
        return dto;
    }

    public List<UserSecurityProfileDto> getAllProfileDto(List<UserSecurity> aList) {
        List<UserSecurityProfileDto> res = new ArrayList<>();
        aList.forEach(a -> {
            res.add(this.getProfileDto(a));
        });
        return res;
    }

    public UserSecurityProfileDto getProfileDto(UserSecurity e) {
        UserSecurityProfileDto dto = new UserSecurityProfileDto();
        //dto.setId(e.getId());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setPrivacy(e.getPrivacy().name());
        dto.setUsername(e.getUsername());
        if (e.getPrivacy() == PrivacyEnum.PUBLIC) {
            dto.setBiography(e.getBiography());
            dto.setBirth(e.getBirth());
            dto.setUrl(e.getUrl());
            dto.setNameFormatted(this.getName(e));
            dto.setProfileImage(e.getProfileImageUrl());
        }
        return dto;
    }

    public List<UserSecuritySimplifiedDto> getAllUserSecuritySimplifiedDto(List<UserSecurity> aList) {
        List<UserSecuritySimplifiedDto> res = new ArrayList<>();
        aList.forEach(a -> {
            res.add(this.getUserSecuritySimplifiedDto(a));
        });
        return res;
    }

    public UserSecuritySimplifiedDto getUserSecuritySimplifiedDto(UserSecurity e) {
        UserSecuritySimplifiedDto dto = new UserSecuritySimplifiedDto();
        dto.setId(e.getId());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setUsername(e.getUsername());
        return dto;
    }

    private String getName(UserSecurity e) {
        String name = e.getFirstName() != null ? e.getFirstName() + " " : "";
        name += e.getMiddleName() != null ? e.getMiddleName() + " " : "";
        name += e.getLastName() != null ? e.getLastName()  : "";
        return name;
    }

}
