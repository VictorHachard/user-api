package com.user.service;

import com.user.dto.CountryDto;
import com.user.dto.GroupDto;
import com.user.model.entities.Address;
import com.user.model.entities.Country;
import com.user.model.entities.Group;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class CountryService extends AbstractService<Country, CountryRepository> {

    @Override
    public Page<Country> getAllBy(Pageable pageable, String searchBy, String searchValue) {
        switch (searchBy) {
            case "name":
                return this.getRepository().findByNameContaining(searchValue, pageable);
            default:
                return super.getAllBy(pageable, searchBy, searchValue);
        }
    }

    @Override
    public void create(AbstractValidator abstractValidator) {

    }

    public Country create(String country) {
        Country c = countryFacade.newInstance(country);
        this.getRepository().save(c);
        return c;
    }

    public List<CountryDto> getAllActiveDto(Integer pageIndex,
                                            Integer pageSize,
                                            String sortBy,
                                            String orderBy,
                                            String searchBy,
                                            String searchValue) {
        if (!searchValue.equals("null")) {
            searchValue = searchValue.toUpperCase();
        }
        List<Country> countryList = this.getAll(pageIndex, pageSize, sortBy, orderBy, searchBy, searchValue);
//        groupList.removeIf(p -> !p.isActive());
        return this.getMapper().getAllDto(countryList);
    }

}
