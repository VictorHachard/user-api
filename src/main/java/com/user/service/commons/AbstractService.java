package com.user.service.commons;

import com.user.dto.commons.Dto;
import com.user.init.AbstractAutowire;
import com.user.init.InitMap;
import com.user.init.MapTypeEnum;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.UserSecurity;
import com.user.model.repositories.commons.AbstractRepository;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
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

    protected UserSecurity getUser() {
        return null;
    }

    protected AbstractMapper getMapper() {
        return InitMap.get(this.getClass(), MapTypeEnum.MAPPER);
    }

    public long count() {
        return this.getAbstractRepository().count();
    }

    public Page<I> getAllBy(Pageable pageable, String searchBy, String searchValue) {
        AbstractRepository repository = this.getAbstractRepository();
        return repository.findAll(pageable);
    }

    public List<I> getAll(Integer pageIndex, Integer pageSize, String sortBy, String orderBy, String searchBy, String searchValue) {
        List<String> errorStrList = new ArrayList();
        if (pageIndex < 0) {
            errorStrList.add("Page index must not be less than zero!");
        } if (pageSize < 1) {
            errorStrList.add("Page size must not be less than one!");
        } if (!orderBy.equals("desc") && !orderBy.equals("asc")) {
            errorStrList.add("Order by must be equal to 'asc' or 'desc'!");
        }
        if (!errorStrList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorStrList.size() == 1 ? errorStrList.get(0) : errorStrList.toString());
        }
        AbstractRepository repository = this.getAbstractRepository();
        Pageable paging;
        if (orderBy.equals("desc")) {
            paging = PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).descending());
        } else {
            paging = PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending());
        }
        Page<I> pagedResult = null;
        try {
            pagedResult = this.getAllBy(paging, searchBy, searchValue);
            //pagedResult = repository.findAll(paging);
        } catch (PropertyReferenceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
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

    public List<Dto> getAllDto(Integer pageNo, Integer pageSize, String sortBy, String orderBy, String searchBy, String searchValue) {
        return (List<Dto>) this.getMapper().getAllDto(this.getAll(pageNo, pageSize, sortBy, orderBy, searchBy, searchValue));
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