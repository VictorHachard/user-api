package com.user.model.facades;

import com.user.model.entities.Theme;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class ThemeFacade extends AbstractFacade<Theme> {

    public Theme newInstance(String name, boolean active, int order) {
        Theme res = super.newInstance();
        res.setName(name);
        res.setActive(active);
        res.setOrder(order);
        return res;
    }

    public void updateInstance(Theme theme, String name, boolean active) {
        theme.setName(name);
        theme.setActive(active);
    }

}
