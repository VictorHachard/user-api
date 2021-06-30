package com.user.validator;

import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ThemeValidator extends AbstractValidator {

    @NotNull(message = "")
    @NotEmpty
    String name;

    @NotNull(message = "")
    @NotEmpty
    String imageUrl;

    @NotNull(message = "")
    @NotEmpty
    String primaryColor;

    @NotNull(message = "")
    @NotEmpty
    String secondaryColor;

    @NotNull(message = "")
    @NotEmpty
    String tertiaryColor;

    @NotNull(message = "")
    @NotEmpty
    String quaternaryColor;

    @NotNull(message = "")
    @NotEmpty
    String primaryTextColor;

    @NotNull(message = "")
    @NotEmpty
    String secondaryTextColor;

    /*@NotNull(message = "")
    Boolean isActive;

    @Min(0)
    Integer order;*/

}