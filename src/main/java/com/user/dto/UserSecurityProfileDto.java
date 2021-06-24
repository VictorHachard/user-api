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
public class UserSecurityProfileDto extends Dto {

    String username;

    String nameFormatted;

    String privacy;

    Date birth;

    String url;

    String biography;

    String profileImage;

    List<EmailDto> emailList;

    List<GroupDto> groupDtoList;

}
