package com.user.model.facades;

import com.user.model.entities.Theme;
import com.user.model.facades.commons.AbstractFacade;
import com.user.utils.Utils;
import org.springframework.stereotype.Component;

import java.awt.*;

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

        /* Dark or light */
        if (Utils.brightness(Color.decode(primaryColor)) > 130) { //light
            res.setPrimaryAlertSuccessColor("#d1e7dd");
            res.setSecondaryAlertSuccessColor("#badbcc");
            res.setTertiaryAlertSuccessColor("#0f5132");
            res.setPrimaryAlertWarningColor("#fff3cd");
            res.setSecondaryAlertWarningColor("#ffecb5");
            res.setTertiaryAlertWarningColor("#664d03");
            res.setPrimaryAlertDangerColor("#f8d7da");
            res.setSecondaryAlertDangerColor("#f5c2c7");
            res.setTertiaryAlertDangerColor("#842029");
            res.setPrimaryAlertPrimaryColor("#cce5ff");
            res.setSecondaryAlertPrimaryColor("#b8daff");
            res.setTertiaryAlertPrimaryColor("#004085");
        } else { //dark
            res.setPrimaryAlertSuccessColor("#17361EFF");
            res.setSecondaryAlertSuccessColor("#296336FF");
            res.setTertiaryAlertSuccessColor("#A8EAB7FF");
            res.setPrimaryAlertWarningColor("#513E00FF");
            res.setSecondaryAlertWarningColor("#A77E00FF");
            res.setTertiaryAlertWarningColor("#FBDB7FFF");
            res.setPrimaryAlertDangerColor("#400B10FF");
            res.setSecondaryAlertDangerColor("#811722FF");
            res.setTertiaryAlertDangerColor("#E69BA2FF");
            res.setPrimaryAlertPrimaryColor("#262a2b");
            res.setSecondaryAlertPrimaryColor("#003b7b");
            res.setTertiaryAlertPrimaryColor("#7cc3ff");
        }

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
