package com.user.validator;

import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class UpdateEmailPreferencesValidator extends AbstractValidator {

    String emailPreferences;

}