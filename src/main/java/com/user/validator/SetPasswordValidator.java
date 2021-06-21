package com.user.validator;

import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class SetPasswordValidator extends AbstractValidator {

    @NotNull(message = "")
    @NotEmpty
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{6,}", message = "Password must contain at least one lowercase, uppercase, numeric, special character and between six and sixteen characters")
    String oldPassword;

    @NotNull(message = "")
    @NotEmpty
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{6,}", message = "Password must contain at least one lowercase, uppercase, numeric, special character and between six and sixteen characters")
    String newPassword;

}
