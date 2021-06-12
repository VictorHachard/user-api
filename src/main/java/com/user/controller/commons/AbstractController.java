package com.user.controller.commons;

import com.user.init.InitMap;
import com.user.init.MapTypeEnum;
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
public abstract class AbstractController<E, D> {

    protected AbstractService getService() {
        return InitMap.get(this.getClass(), MapTypeEnum.SERVICE);
    }

    @GetMapping("/count")
    public long count() {
        return this.getService().count();
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
    public D getDto(@PathVariable("id") long id) {
        return (D) this.getService().getDto(id);
    }

    @GetMapping("dto")
    public List<D> getAllDto(@RequestParam(defaultValue = "0") Integer pageIndex,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(defaultValue = "id") String sortBy,
                          @RequestParam(defaultValue = "asc") String orderBy) {
        return (List<D>) this.getService().getAllDto(pageIndex, pageSize, sortBy, orderBy);
    }

    @GetMapping("raw/{id}")
    public E get(@PathVariable("id") long id) {
        return (E) this.getService().get(id);
    }

    @GetMapping("raw")
    public List<E> getAll(@RequestParam(defaultValue = "0") Integer pageIndex,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(defaultValue = "id") String sortBy,
                       @RequestParam(defaultValue = "asc") String orderBy) {
        return (List<E>) this.getService().getAll(pageIndex, pageSize, sortBy, orderBy);
    }

}
