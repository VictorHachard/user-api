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
public abstract class AbstractService<I> extends AbstractAutowire {

    public List<I> getAll() {
        AbstractRepository repository = InitRepository.get(this.getClass());
        if (repository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        } else {
            return (List<I>) repository.findAll();
        }
    }

    public I get(long id) {
        AbstractRepository repository = InitRepository.get(this.getClass());
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        } else {
            return (I) repository.findById(id).get();
        }
    }

    public Dto getDto(long id) {
        AbstractMapper mapper = InitMapper.get(this.getClass());
        return (Dto) mapper.getDto(this.get(id));
    }

    public List<Dto> getAllDto() {
        AbstractMapper mapper = InitMapper.get(this.getClass());
        return (List<Dto>) mapper.getAllDto(this.getAll());
    }

    public void add(AbstractValidator abstractValidator) {

    }

    public void update(AbstractValidator abstractValidator, int id) {
        AbstractRepository repository = InitRepository.get(this.getClass());
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        }
    }

    public void delete(long id) {
        AbstractRepository repository = InitRepository.get(this.getClass());
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no " + this.getClass().getSimpleName() + " in the database");
        } else {
            repository.deleteById(id);
        }
    }

}