package com.user.dto;

import com.user.dto.commons.Dto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ThemeDto extends Dto {

    String name;

    String image;

    boolean active;

    int order;

    String primaryColor;

    String secondaryColor;

    String tertiaryColor;

    String quaternaryColor;

    String primaryTextColor;

    String secondaryTextColor;

    String primaryAlertSuccessColor;

    String secondaryAlertSuccessColor;

    String tertiaryAlertSuccessColor;

    String primaryAlertWarningColor;

    String secondaryAlertWarningColor;

    String tertiaryAlertWarningColor;

    String primaryAlertDangerColor;

    String secondaryAlertDangerColor;

    String tertiaryAlertDangerColor;

    String primaryAlertPrimaryColor;

    String secondaryAlertPrimaryColor;

    String tertiaryAlertPrimaryColor;

}