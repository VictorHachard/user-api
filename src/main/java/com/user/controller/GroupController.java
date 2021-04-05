package com.user.controller;

import com.user.model.entities.Group;
import com.user.model.repositories.GroupRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("api/v1/group/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class GroupController {

    @Autowired
    GroupRepository groupRepository;

    @PostMapping("/create")
    public void create(@Valid @RequestBody GroupValidator validator) {
        boolean existsByName = groupRepository.existsByName(validator.getName());
        if (existsByName) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This group name already exist");
        } else {
            Group group = new Group();
            group.setName(validator.getName());
            groupRepository.save(group);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Success group created");
        }
    }

    @GetMapping("/count")
    public long count() {
        return groupRepository.count();
    }

    @GetMapping("/{id}")
    public Group get(@PathVariable("id") long id) {
        if (groupRepository.existsById(id)) {
            return groupRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user doesn't exist in the database");
        }
    }

    @GetMapping("")
    public List<Group> getAll() {
        if (groupRepository.count() >= 1) {
            return groupRepository.findAll();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no user in the database");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Success user deleted");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user doesn't exist in the database");
        }
    }

    @PutMapping("/{id}")
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
    }

}

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
class GroupValidator {

    @NotNull(message = "")
    @NotEmpty
    String name;

}
