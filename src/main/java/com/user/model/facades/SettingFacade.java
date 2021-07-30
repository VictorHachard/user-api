package com.user.model.facades;

import com.user.model.entities.Setting;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class SettingFacade extends AbstractFacade<Setting> {

    public Setting newInstance(String name, Boolean active, Boolean canUpdate) {
        Setting res = super.newInstance();
        res.setName(name);
        res.setActive(active);
        res.setCanUpdate(canUpdate);
        return res;
    }

}