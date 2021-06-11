package com.user.validator;

import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class EmailValidator extends AbstractValidator {

    @NotNull(message = "")
    @NotEmpty
    @Email
    String email;

    @NotNull(message = "")
    @NotEmpty
    String priorityEnum;

}
