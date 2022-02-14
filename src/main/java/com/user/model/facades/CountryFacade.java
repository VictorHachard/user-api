package com.user.model.facades;

import com.user.model.entities.Country;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class CountryFacade extends AbstractFacade<Country> {

    public Country newInstance(String name) {
        Country res = super.newInstance();
        res.setName(name);
        return res;
    }

}