package com.user.controller.commons;

import com.user.dto.commons.Dto;
import com.user.init.InitService;
import com.user.model.entities.commmons.AbstractEntity;
import com.user.service.commons.AbstractService;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// Lombok
@FieldDefaults(level = AccessLevel.PROTECTED)
@Log
public abstract class AbstractController {

    private AbstractService getService() {
        return InitService.get(this.getClass());
    }

    @GetMapping("/count")
    public long count() {
        return this.getService().count();
    }

    @PostMapping("/create")
    public void create(@Valid @RequestBody AbstractValidator abstractValidator) {
        this.getService().create(abstractValidator);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        this.getService().delete(id);
    }

    @PutMapping("{id}")
    public void update(@Valid @RequestBody AbstractValidator abstractValidator, @PathVariable("id") long id) {
        this.getService().update(abstractValidator, id);
    }

    @GetMapping("dto/{id}")
    public Dto getDto(@PathVariable("id") long id) {
        return this.getService().getDto(id);
    }

    @GetMapping("dto")
    public List getAllDto() {
        return this.getService().getAllDto();
    }

    @GetMapping("raw/{id}")
    public AbstractEntity get(@PathVariable("id") long id) {
        return (AbstractEntity) this.getService().get(id);
    }

    @GetMapping("raw")
    public List getAll() {
        return this.getService().getAll();
    }

}
