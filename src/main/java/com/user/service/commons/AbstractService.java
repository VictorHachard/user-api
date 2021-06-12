package com.user.service.commons;

import com.user.dto.commons.Dto;
import com.user.init.AbstractAutowire;
import com.user.init.InitMap;
import com.user.init.MapTypeEnum;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.repositories.commons.AbstractRepository;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

// Lombok
@FieldDefaults(level = AccessLevel.PROTECTED)
@Log
public abstract class AbstractService<I, T> extends AbstractAutowire {

    private AbstractRepository getAbstractRepository() {
        return InitMap.get(this.getClass(), MapTypeEnum.REPOSITORY);
    }

    protected T getRepository() {
        return (T) InitMap.get(this.getClass(), MapTypeEnum.REPOSITORY);
    }

    protected AbstractMapper getMapper() {
        return InitMap.get(this.getClass(), MapTypeEnum.MAPPER);
    }

    public long count() {
        return this.getAbstractRepository().count();
    }

    public List<I> getAll(Integer pageNo, Integer pageSize, String sortBy, String orderBy) {
        AbstractRepository repository = this.getAbstractRepository();
        Pageable paging;
        if (orderBy.equals("desc")) {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        }
        Page<I> pagedResult = repository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<I>();
        }
    }

    public I get(long id) {
        AbstractRepository repository = this.getAbstractRepository();
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        } else {
            return (I) repository.findById(id).get();
        }
    }

    public Dto getDto(long id) {
        return (Dto) this.getMapper().getDto(this.get(id));
    }

    public List<Dto> getAllDto(Integer pageNo, Integer pageSize, String sortBy, String orderBy) {
        return (List<Dto>) this.getMapper().getAllDto(this.getAll(pageNo, pageSize, sortBy, orderBy));
    }

    public void create(AbstractValidator abstractValidator) {
    }

    public void update(AbstractValidator abstractValidator, long id) {
        if (!this.getAbstractRepository().existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        }
    }

    public void delete(long id) {
        AbstractRepository repository = this.getAbstractRepository();
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        } else {
            repository.deleteById(id);
        }
    }

    protected void responseStatus(HttpStatus hs, String response) {
        throw new ResponseStatusException(hs, response);
    }

    /*protected void responseStatus(HttpStatus hs) {
        String response = "";
        switch (hs) {
            case OK:
                response = "";
                break;
            case NO_CONTENT:
                response = "Success " + this.getClass().getSimpleName().toLowerCase() + " created";
        }
        throw new ResponseStatusException(hs, response);
    }*/

}