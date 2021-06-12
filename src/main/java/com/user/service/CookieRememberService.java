package com.user.service;

import com.user.model.entities.Email;
import com.user.model.repositories.EmailRepository;
import com.user.service.commons.AbstractService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class CookieRememberService extends AbstractService<Email, EmailRepository> {

}
