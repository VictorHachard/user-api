package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.HtmlTextHistoryDto;
import com.user.model.entities.HtmlTextHistory;
import com.user.service.HtmlTextHistoryService;
import com.user.validator.HtmlTextValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/html-text-history/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class HtmlTextHistoryController extends AbstractController<HtmlTextHistory, HtmlTextHistoryDto> {

    @PostMapping("create")
    public void create(@Valid @RequestBody HtmlTextValidator validator) {
        this.getService().create(validator);
    }

    @PostMapping("add/{id}")
    public void add(@Valid @RequestBody HtmlTextValidator validator, @PathVariable("id") long id) {
        HtmlTextHistoryService service = (HtmlTextHistoryService) this.getService();
        service.add(validator, id);
    }

}
