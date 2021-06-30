package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.SecurityLogDto;
import com.user.model.entities.SecurityLog;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/security-log/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class SecurityLogController extends AbstractController<SecurityLog, SecurityLogDto> {

}
