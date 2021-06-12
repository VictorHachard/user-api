package com.user.init;

import com.user.mapper.EmailMapper;
import com.user.mapper.GroupMapper;
import com.user.mapper.PasswordMapper;
import com.user.mapper.UserSecurityMapper;
import com.user.model.facades.EmailFacade;
import com.user.model.facades.GroupFacade;
import com.user.model.facades.PasswordFacade;
import com.user.model.facades.UserSecurityFacade;
import com.user.model.repositories.EmailRepository;
import com.user.model.repositories.GroupRepository;
import com.user.model.repositories.PasswordRepository;
import com.user.model.repositories.UserSecurityRepository;
import com.user.service.EmailService;
import com.user.service.GroupService;
import com.user.service.PasswordService;
import com.user.service.UserSecurityService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

// Lombok
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractAutowire {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    EmailMapper emailMapper;

    @Autowired
    EmailService emailService;

    @Autowired
    EmailFacade emailFacade;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    GroupService groupService;

    @Autowired
    GroupFacade groupFacade;

    @Autowired
    UserSecurityRepository userSecurityRepository;

    @Autowired
    UserSecurityMapper userSecurityMapper;

    @Autowired
    UserSecurityService userSecurityService;

    @Autowired
    UserSecurityFacade userSecurityFacade;

    @Autowired
    PasswordRepository passwordRepository;

    @Autowired
    PasswordMapper passwordMapper;

    @Autowired
    PasswordService passwordService;

    @Autowired
    PasswordFacade passwordFacade;

}
