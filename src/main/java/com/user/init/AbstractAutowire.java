package com.user.init;

import com.user.mapper.EmailMapper;
import com.user.model.repositories.EmailRepository;
import com.user.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAutowire {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    EmailMapper emailMapper;

    @Autowired
    EmailService emailService;

}
