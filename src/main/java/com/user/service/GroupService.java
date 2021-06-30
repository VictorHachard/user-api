package com.user.service;

import com.user.dto.GroupDto;
import com.user.model.entities.Group;
import com.user.model.repositories.GroupRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.GroupValidator;
import com.user.validator.UpdateGroupActiveValidator;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class GroupService extends AbstractService<Group, GroupRepository> {

    @Override
    public void create(AbstractValidator abstractValidator) {
        GroupValidator validator = (GroupValidator) abstractValidator;
        this.create(validator.getName(), validator.getColor());
        this.responseStatus(HttpStatus.NO_CONTENT, "Success " + this.getClass().getSimpleName().toLowerCase() + " created");
    }

    public Group create(String name, String color) {
        if (this.getRepository().existsByName(name)) {
            this.responseStatus(HttpStatus.BAD_REQUEST, "This group name already exist");
        }
        Group g = groupFacade.newInstance(name, color,false, 0);
        this.getRepository().save(g);
        return g;
    }

    public List<GroupDto> getAllActiveDto() {
        List<Group> groupList = this.getAll();
        groupList.removeIf(p -> !p.isActive());
        return this.getMapper().getAllDto(groupList);
    }

    public void updateActive(long id, UpdateGroupActiveValidator validator) {
        Group g = this.get(id);
        g.setActive(validator.getActive());
        this.getRepository().save(g);
        this.responseStatus(HttpStatus.NO_CONTENT, "Success group active");
    }

}
