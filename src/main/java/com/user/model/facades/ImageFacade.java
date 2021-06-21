package com.user.model.facades;

import com.user.model.entities.Image;
import com.user.model.facades.commons.AbstractFacade;
import org.springframework.stereotype.Component;

@Component
public class ImageFacade extends AbstractFacade<Image> {

    public Image newInstance(String url) {
        Image res = super.newInstance();
        res.setUrl(url);
        return res;
    }

}
