package com.user.controller;

import com.user.controller.commons.AbstractController;
import com.user.dto.SecurityLogDto;
import com.user.interceptor.AuthorisationForOverride;
import com.user.interceptor.AuthorisationForOverrideColumn;
import com.user.model.entities.SecurityLog;
import com.user.model.entities.enums.RoleEnum;
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
// Authorisation
@AuthorisationForOverrideColumn(table = {
        @AuthorisationForOverride(name = "count", roles = {RoleEnum.ROLE_USER}),
        @AuthorisationForOverride(name = "create", roles = {RoleEnum.ROLE_OWNER}), // Block
        @AuthorisationForOverride(name = "update", roles = {RoleEnum.ROLE_OWNER}), // Block
        @AuthorisationForOverride(name = "delete", roles = {RoleEnum.ROLE_OWNER}), // Block
        @AuthorisationForOverride(name = "getDto", roles = {RoleEnum.ROLE_OWNER}), // Block
        @AuthorisationForOverride(name = "getAllDto", roles = {RoleEnum.ROLE_USER}),
        @AuthorisationForOverride(name = "get", roles = {RoleEnum.ROLE_OWNER}), // Block
        @AuthorisationForOverride(name = "getAll", roles = {RoleEnum.ROLE_OWNER}) // Block
})
public class SecurityLogController extends AbstractController<SecurityLog, SecurityLogDto> {

}
