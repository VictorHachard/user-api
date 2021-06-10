package com.user.controller.commons;

import com.user.dto.commons.Dto;
import com.user.init.InitService;
import com.user.model.entities.commmons.AbstractEntity;
import com.user.service.commons.AbstractService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// Lombok
@FieldDefaults(level = AccessLevel.PROTECTED)
@Log
public abstract class AbstractController {

    AbstractService service = InitService.get(this.getClass());

    @GetMapping("dto/{id}")
    public Dto getDto(@PathVariable("id") long id) {
        return service.getDto(id);
    }

    @GetMapping("dto")
    public List getAllDto() {
        return service.getAllDto();
    }

    @GetMapping("raw/{id}")
    public AbstractEntity get(@PathVariable("id") long id) {
        return (AbstractEntity) service.get(id);
    }

    @GetMapping("raw")
    public List getAll() {
        return service.getAll();
    }

}
