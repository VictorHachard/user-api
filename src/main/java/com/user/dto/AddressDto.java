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
public class AddressDto extends Dto {

    String alias;

    Boolean _default;

    String name;

    String building; // Building, floor...

    String street; // Street, house number, box number

    String postcode; // Postcode and name of municipality

}
