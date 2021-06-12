package com.user.validator;

import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Email;

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class UserSecurityValidator extends AbstractValidator {

    @NotNull(message = "")
    @NotEmpty
    String username;

    @NotNull(message = "")
    @NotEmpty
    @Email
    String email;

    @NotNull(message = "")
    @NotEmpty
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{6,}", message = "Password must contain at least one lowercase, uppercase, numeric, special character and between six and sixteen characters")
    String password;

}
