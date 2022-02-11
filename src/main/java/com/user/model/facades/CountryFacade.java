package com.user.model.facades;

import com.user.model.entities.Country;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class CountryFacade extends AbstractFacade<Country> {

    public Country newInstance(String country) {
        Country res = super.newInstance();
        res.setCountry(country);
        return res;
    }

}