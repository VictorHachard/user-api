package com.user.mapper;

import com.user.dto.AddressDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Address;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class AddressMapper extends AbstractMapper<AddressDto, Address> {

    @Override
    public AddressDto getDto(Address e) {
        AddressDto dto = super.getDto(e);
        dto.setName(e.getName());
        dto.set_default(e.get_default());
        dto.setAlias(e.getAlias());
        dto.setBuilding(e.getBuilding());
        dto.setStreet(e.getStreet());
        dto.setPostcode(e.getPostcode());
        dto.setCountryDto(countryMapper.getDto(e.getCountry()));
        return dto;
    }

    private String generateAddress(Address e) {
        return  "<p>" + e.getAlias() + "<\\p>\n" +
                "<p>" + e.getName() + "<\\p>\n" +
                "<p>" + e.getBuilding() + "<\\p>\n" +
                "<p>" + e.getStreet() + "<\\p>\n" +
                "<p>" + e.getPostcode() + "<\\p>\n" +
                "<p>" + e.getCountry().getName() + "<\\p>\n";
    }

}
