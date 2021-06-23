package com.user.validator;

import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class LoginFromCookieValidator extends AbstractValidator {

    @NotNull(message = "")
    @NotEmpty
    @Size(min = 60, max = 64)
    String token;

}
