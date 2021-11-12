package com.user.model.facades;

import com.user.model.entities.RecoveryCode;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class RecoveryCodeFacade extends AbstractFacade<RecoveryCode> {

    public RecoveryCode newInstance() {
        RecoveryCode res = super.newInstance();
        res.setUsed(false);
        return res;
    }

}