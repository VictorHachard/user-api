package com.user.service.commons;

import com.user.dto.commons.Dto;
import com.user.init.AbstractAutowire;
import com.user.init.InitMapper;
import com.user.init.InitRepository;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.repositories.commons.AbstractRepository;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// Lombok
@FieldDefaults(level = AccessLevel.PROTECTED)
@Log
public abstract class AbstractService<I, T> extends AbstractAutowire {

    private AbstractRepository getAbstractRepository() {
        return InitRepository.get(this.getClass());
    }

    protected T getRepository() {
        return (T) InitRepository.get(this.getClass());
    }

    protected AbstractMapper getMapper() {
        return InitMapper.get(this.getClass());
    }

    public long count() {
        return this.getAbstractRepository().count();
    }

    public List<I> getAll() {
        AbstractRepository repository = this.getAbstractRepository();
        if (repository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        } else {
            return (List<I>) repository.findAll();
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

    public List<Dto> getAllDto() {
        return (List<Dto>) this.getMapper().getAllDto(this.getAll());
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
        /*switch (hs) {
            case OK:
                response = "";
                break;
            case NO_CONTENT:
                response = "Success " +  + " created";
        }*/
        throw new ResponseStatusException(hs, response);
    }

}