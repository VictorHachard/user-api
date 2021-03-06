package com.user.service;

import com.user.model.entities.Address;
import com.user.model.entities.Country;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.SecurityLogEnum;
import com.user.model.repositories.AddressRepository;
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
import org.springframework.web.server.ResponseStatusException;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class AddressService extends AbstractService<Address, AddressRepository> {

    @Override
    public Page<Address> getAllBy(Pageable pageable, String searchBy, String searchValue) {
        switch (searchBy) {
            case "name":
                return this.getRepository().findByNameContaining(searchValue, pageable);
            case "alias":
                return this.getRepository().findByAliasContaining(searchValue, pageable);
            default:
                this.responseStatus(HttpStatus.BAD_REQUEST, "By " + searchBy + " is incorrect");
        }
        return null;
    }

    @Override
    public void create(AbstractValidator abstractValidator) {
        AddressValidator validator = (AddressValidator) abstractValidator;
        UserSecurity u = this.getUser();
        if (!countryRepository.existsByName(validator.getCountry())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country " + validator.getCountry() + " is not in the database");
        }
        Country c = countryRepository.findByName(validator.getCountry()).get();
        Address a = this.create(validator.getAlias(), false, validator.getName(), validator.getBuilding(), validator.getStreet(), validator.getPostcode(), c);
        u.addAddress(a);
        userSecurityRepository.save(u);
        securityLogService.create(SecurityLogEnum.ADDRESS_ADDED, "Address " + a.getAlias() + " added");
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }

    public Address create(String alias,
                          Boolean _default,
                          String name,
                          String building,
                          String street,
                          String postcode,
                          Country country) {
        Address a = addressFacade.newInstance(alias, _default, name, building, street, postcode, country);
        this.getRepository().save(a);
        return a;
    }

    @Override
    public void delete(long id) {
        if (!this.getRepository().existsById(id)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        } else {
            UserSecurity u = this.getUser();
            Address a = this.getRepository().findById(id).get();
            if (!u.getAddressList().contains(a)) {
                this.responseStatus(HttpStatus.BAD_REQUEST, "This user has not this address");
            } else {
                u.getAddressList().remove(a);
                this.getRepository().deleteById(id);
                securityLogService.create(SecurityLogEnum.ADDRESS_REMOVED, "Address " + a.getAlias() + " deleted");
                this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " deleted");
            }
        }
    }

}
