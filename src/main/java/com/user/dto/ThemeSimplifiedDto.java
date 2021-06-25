package com.user.dto;

import com.user.dto.commons.Dto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ThemeSimplifiedDto extends Dto {

    String name;

    String image;

    String primaryColor;

    String secondaryColor;

    String tertiaryColor;

    String quaternaryColor;

    String primaryTextColor;

    String secondaryTextColor;

}
