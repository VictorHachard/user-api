package com.user.model.facades;

import com.user.model.entities.HtmlTextHistory;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class HtmlTextHistoryFacade extends AbstractFacade<HtmlTextHistory> {

    public HtmlTextHistory newInstance(String name) {
        HtmlTextHistory res = super.newInstance();
        res.setName(name);
        return res;
    }

}