package com.user.model.facades;

import com.user.model.entities.Address;
import com.user.model.entities.Country;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class AddressFacade extends AbstractFacade<Address> {

    public Address newInstance(String alias,
                               Boolean _default,
                               String name,
                               String building,
                               String street,
                               String postcode,
                               Country country) {
        Address res = super.newInstance();
        res.setName(name);
        res.set_default(_default);
        res.setAlias(alias);
        res.setBuilding(building);
        res.setStreet(street);
        res.setPostcode(postcode);
        res.setCountry(country);
        return res;
    }

}