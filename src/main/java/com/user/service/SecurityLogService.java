package com.user.service;

import com.user.model.entities.SecurityLog;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.SecurityLogEnum;
import com.user.model.repositories.SecurityLogRepository;
import com.user.service.commons.AbstractService;
import com.user.validator.commons.AbstractValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class SecurityLogService extends AbstractService<SecurityLog, SecurityLogRepository> {

    @Override
    public void create(AbstractValidator abstractValidator) { }

    public SecurityLog create(SecurityLogEnum sl, UserSecurity us, String info) {
        SecurityLog s = securityLogFacade.newInstance(sl, us, info);
        this.getRepository().save(s);
        return s;
    }

    public Page<SecurityLog> getAllDtoByUser(UserSecurity u, Integer pageIndex, Integer pageSize) {
        List<String> errorStrList = new ArrayList();
        if (pageIndex < 0) {
            errorStrList.add("Page index must not be less than zero!");
        } if (pageSize < 1) {
            errorStrList.add("Page size must not be less than one!");
        }
        if (!errorStrList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorStrList.size() == 1 ? errorStrList.get(0) : errorStrList.toString());
        }
        Pageable paging = PageRequest.of(pageIndex, pageSize, Sort.by("id").descending());
        return this.getRepository().findByUserSecurityId(u.getId(), paging);
    }

}
