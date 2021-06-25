package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.GroupDto;
import com.user.dto.ThemeDto;
import com.user.model.entities.Group;
import com.user.service.GroupService;
import com.user.service.ThemeService;
import com.user.validator.GroupValidator;
import com.user.validator.UpdateGroupActiveValidator;
import com.user.validator.UpdateThemeActiveValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/group/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class GroupController extends AbstractController<Group, GroupDto> {

    @PostMapping("/create")
    public void create(@Valid @RequestBody GroupValidator validator) {
        this.getService().create(validator);
    }

    @GetMapping("/dto/active")
    public List<GroupDto> getAllActiveDto() {
        GroupService service = (GroupService) this.getService();
        return service.getAllActiveDto();
    }

    /*@PutMapping("/{id}")
    public void update(@PathVariable("id") long id, @Valid @RequestBody GroupValidator validator) {
        if (groupRepository.existsById(id)) {
            boolean existsByName = groupRepository.existsByName(validator.getName());
            if (existsByName) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This group name already exist");
            } else {
                Group group = groupRepository.findById(id).get();
                group.setName(validator.getName());
                groupRepository.save(group);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Success user updated");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user doesn't exist in the database");
        }
    }*/

    @PostMapping("update/active/{id}")
    public void updateActive(@Valid @RequestBody UpdateGroupActiveValidator validator, @PathVariable("id") long id) {
        GroupService service = (GroupService) this.getService();
        service.updateActive(id, validator);
    }

}
