package com.user.service;

import com.user.model.entities.HtmlText;
import com.user.model.entities.HtmlTextHistory;
import com.user.model.repositories.HtmlTextHistoryRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.HtmlTextValidator;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class HtmlTextHistoryService extends AbstractService<HtmlTextHistory, HtmlTextHistoryRepository> {

    @Override
    public Page<HtmlTextHistory> getAllBy(Pageable pageable, String searchBy, String searchValue) {
        switch (searchBy) {
            case "name":
                return this.getRepository().findByNameContaining(searchValue, pageable);
            case "null":
                return super.getAllBy(pageable, searchBy, searchValue);
            default:
                this.responseStatus(HttpStatus.BAD_REQUEST, "By " + searchBy + " is incorrect");
        }
        return null;
    }

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

    public void add(AbstractValidator abstractValidator, long id) {
        HtmlTextValidator validator = (HtmlTextValidator) abstractValidator;
        HtmlTextHistory htmlTextHistory = this.get(id);
        HtmlText htmlText = htmlTextService.create(validator.getHtmlContent());
        htmlTextHistory.addHtmlText(htmlText);
        this.getRepository().save(htmlTextHistory);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }

}
