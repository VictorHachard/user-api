package com.user.model.facades;

import com.user.model.entities.Group;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class GroupFacade extends AbstractFacade<Group> {

    public Group newInstance(String name) {
        Group res = super.newInstance();
        res.setName(name);
        return res;
    }

}