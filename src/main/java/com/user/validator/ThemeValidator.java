package com.user.validator;

import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
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
    Boolean isActive;

    @Min(0)
    Integer order;

}