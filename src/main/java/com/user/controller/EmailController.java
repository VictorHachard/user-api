package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.mapper.EmailMapper;
import com.user.model.repositories.EmailRepository;
import com.user.service.EmailService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/email/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class EmailController extends AbstractController {

    /*@GetMapping("dto")
    public List<Dto> getAllDto() {
        if (emailRepository.count() >= 1) {
            return emailMapper.getAllDto(emailRepository.findAll());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no user in the database");
        }
    }*/






}
