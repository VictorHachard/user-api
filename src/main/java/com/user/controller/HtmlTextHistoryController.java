package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.HtmlTextHistoryDto;
import com.user.model.entities.HtmlTextHistory;
import com.user.validator.GroupValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/htmlTextHistory/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class HtmlTextHistoryController extends AbstractController<HtmlTextHistory, HtmlTextHistoryDto> {

    @PostMapping("/create")
    public void create(@Valid @RequestBody GroupValidator validator) {
        this.getService().create(validator);
    }

}
