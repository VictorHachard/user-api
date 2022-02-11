package com.user.mapper;

import com.user.dto.AddressDto;
import com.user.dto.CountryDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Address;
import com.user.model.entities.Country;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class CountryMapper extends AbstractMapper<CountryDto, Country> {

    @Override
    public CountryDto getDto(Country c) {
        CountryDto dto = super.getDto(c);
        dto.setCountry(c.getCountry());
        return dto;
    }

}
