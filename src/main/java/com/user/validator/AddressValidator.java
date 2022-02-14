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
public class AddressValidator extends AbstractValidator {

    @NotNull(message = "")
    @NotEmpty
    String alias;

    @NotNull(message = "")
    @NotEmpty
    String name;

    String building;

    @NotNull(message = "")
    @NotEmpty
    String street;

    @NotNull(message = "")
    @NotEmpty
    String postcode;

    @NotNull(message = "")
    @NotEmpty
    String country;

}
