package com.user.dto;

import com.user.dto.commons.Dto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class UserSecurityDto extends Dto {

    /*
    Is only used for the login process.
     */
    SessionDto actualSessionDto;

    List<SessionDto> sessionDtoList;

    String username;

    String nameFormatted;

    String privacy;

    Date birth;

    String firstName;

    String middleName;

    String lastName;

    String url;

    String biography;

    ThemeSimplifiedDto themeSimplifiedDto;

    String profileImage;

    List<EmailDto> emailList;

    List<GroupDto> groupDtoList;

    List<RoleDto> roleDtoList;

    List<UserSecuritySimplifiedDto> blockedUserDtoList;

    String emailPreferences;

    Boolean twoFactorEmail;

    List<AddressDto> addressDtoList;

}
