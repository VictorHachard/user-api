package com.user.service;

import com.user.model.entities.Group;
import com.user.model.repositories.GroupRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.GroupValidator;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class GroupService extends AbstractService<Group, GroupRepository> {

    @Override
    public void create(AbstractValidator abstractValidator) {
        GroupValidator validator = (GroupValidator) abstractValidator;

        boolean existsByName = this.getRepository().existsByName(validator.getName());
        if (existsByName) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This group name already exist");
        } else {
            Group g = groupFacade.newInstance(validator.getName());
            this.getRepository().save(g);
            this.responseStatus(HttpStatus.NO_CONTENT, "Success group created");
        }
    }

}
