package com.user.model.facades;

import com.user.model.entities.Role;
import com.user.model.entities.RoleEnum;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class RoleFacade extends AbstractFacade<Role> {

    public Role newInstance(RoleEnum re) {
        Role res = super.newInstance();
        res.setRole(re);
        return res;
    }

}