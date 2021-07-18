package com.user.service;

import com.user.model.entities.HtmlText;
import com.user.model.repositories.HtmlTextRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.HtmlTextValidator;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class HtmlTextService extends AbstractService<HtmlText, HtmlTextRepository> {

    @Override
    public void create(AbstractValidator abstractValidator) {
        HtmlTextValidator validator = (HtmlTextValidator) abstractValidator;
        this.create(validator.getHtmlContent());
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }

    public HtmlText create(String HtmlContent) {
        HtmlText htmlText = htmlTextFacade.newInstance(HtmlContent);
        this.getRepository().save(htmlText);
        return htmlText;
    }

}
