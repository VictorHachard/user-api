package com.user.controller;

import com.user.controller.commons.AbstractController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/email/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class EmailController extends AbstractController {

}
