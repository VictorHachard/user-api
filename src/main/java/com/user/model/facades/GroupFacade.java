package com.user.model.facades;

import com.user.model.entities.Group;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class GroupFacade extends AbstractFacade<Group> {

    public Group newInstance(String name, String color, boolean active, int order) {
        Group res = super.newInstance();
        res.setName(name);
        res.setActive(active);
        res.setOrder(order);
        res.setColor(color);
        return res;
    }

    public void updateInstance(Group group, String name, boolean active) {
        group.setName(name);
        group.setActive(active);
    }

}