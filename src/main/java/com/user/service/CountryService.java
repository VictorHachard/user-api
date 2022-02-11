package com.user.service;

import com.user.model.entities.Address;
import com.user.model.entities.Country;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.SecurityLogEnum;
import com.user.model.repositories.AddressRepository;
import com.user.model.repositories.CountryRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.AddressValidator;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class CountryService extends AbstractService<Country, CountryRepository> {

    @Override
    public Page<Country> getAllBy(Pageable pageable, String searchBy, String searchValue) {
        switch (searchBy) {
            case "country":
                return this.getRepository().findByCountryContaining(searchValue, pageable);
            default:
                this.responseStatus(HttpStatus.BAD_REQUEST, "By " + searchBy + " is incorrect");
        }
        return null;
    }

    @Override
    public void create(AbstractValidator abstractValidator) {

    }

    public Country create(String country) {
        Country c = countryFacade.newInstance(country);
        this.getRepository().save(c);
        return c;
    }

}
