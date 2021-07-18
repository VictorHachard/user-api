package com.user.service;

import com.user.model.entities.HtmlText;
import com.user.model.entities.HtmlTextHistory;
import com.user.model.repositories.HtmlTextHistoryRepository;
import com.user.service.commons.AbstractService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class HtmlTextHistoryService extends AbstractService<HtmlTextHistory, HtmlTextHistoryRepository> {

    /*@Override
    public void create(AbstractValidator abstractValidator) {
        HtmlTextValidator validator = (HtmlTextValidator) abstractValidator;
        this.create(validator.getHtmlContent());
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }*/

    public HtmlTextHistory create(String name, HtmlText htmlText) {
        HtmlTextHistory htmlTextHistory = htmlTextHistoryFacade.newInstance(name);
        htmlTextHistory.addHtmlText(htmlText);
        this.getRepository().save(htmlTextHistory);
        return htmlTextHistory;
    }

}
