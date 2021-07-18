package com.user.model.facades;

import com.user.model.entities.HtmlText;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class HtmlTextFacade extends AbstractFacade<HtmlText> {

    public HtmlText newInstance(String HtmlContent) {
        HtmlText res = super.newInstance();
        res.setHtmlContent(HtmlContent);
        return res;
    }

}