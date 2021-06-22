package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.CookieRememberDto;
import com.user.dto.SecurityLogDto;
import com.user.dto.UserSecurityDto;
import com.user.model.entities.SecurityLog;
import com.user.model.entities.UserSecurity;
import com.user.service.UserSecurityService;
import com.user.validator.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("api/v1/security-log/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class SecurityLogController extends AbstractController<SecurityLog, SecurityLogDto> {

}
