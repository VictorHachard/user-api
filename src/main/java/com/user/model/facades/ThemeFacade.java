package com.user.model.facades;

import com.user.model.entities.Theme;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class ThemeFacade extends AbstractFacade<Theme> {

    public Theme newInstance(String name,
                             boolean active,
                             int order,
                             String primaryColor,
                             String secondaryColor,
                             String tertiaryColor,
                             String quaternaryColor,
                             String primaryTextColor,
                             String secondaryTextColor) {
        Theme res = super.newInstance();
        res.setName(name);
        res.setActive(active);
        res.setOrder(order);
        res.setPrimaryColor(primaryColor);
        res.setSecondaryColor(secondaryColor);
        res.setTertiaryColor(tertiaryColor);
        res.setQuaternaryColor(quaternaryColor);
        res.setPrimaryTextColor(primaryTextColor);
        res.setSecondaryTextColor(secondaryTextColor);
        return res;
    }

    public void updateInstance(Theme theme,
                               String name,
                               String primaryColor,
                               String secondaryColor,
                               String tertiaryColor,
                               String quaternaryColor,
                               String primaryTextColor,
                               String secondaryTextColor) {
        theme.setName(name);
        theme.setPrimaryColor(primaryColor);
        theme.setSecondaryColor(secondaryColor);
        theme.setTertiaryColor(tertiaryColor);
        theme.setQuaternaryColor(quaternaryColor);
        theme.setPrimaryTextColor(primaryTextColor);
        theme.setSecondaryTextColor(secondaryTextColor);
    }

    public void updateInstance(Theme theme, String name, boolean active) {
        theme.setName(name);
        theme.setActive(active);
    }

}
