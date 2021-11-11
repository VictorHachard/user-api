package com.user.init;

import com.user.mapper.*;
import com.user.model.facades.*;
import com.user.model.repositories.*;
import com.user.service.*;
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
    SessionRepository sessionRepository;

    @Autowired
    SessionMapper sessionMapper;

    @Autowired
    SessionService sessionService;

    @Autowired
    SessionFacade sessionFacade;

    @Autowired
    PasswordRepository passwordRepository;

    @Autowired
    PasswordMapper passwordMapper;

    @Autowired
    PasswordService passwordService;

    @Autowired
    PasswordFacade passwordFacade;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleFacade roleFacade;

    @Autowired
    CookieRememberRepository cookieRememberRepository;

    @Autowired
    CookieRememberMapper cookieRememberMapper;

    @Autowired
    CookieRememberService cookieRememberService;

    @Autowired
    CookieRememberFacade cookieRememberFacade;

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    ThemeMapper themeMapper;

    @Autowired
    ThemeService themeService;

    @Autowired
    ThemeFacade themeFacade;

    @Autowired
    SecurityLogRepository securityLogRepository;

    @Autowired
    SecurityLogMapper securityLogMapper;

    @Autowired
    SecurityLogService securityLogService;

    @Autowired
    SecurityLogFacade securityLogFacade;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ImageMapper imageMapper;

    @Autowired
    ImageService imageService;

    @Autowired
    ImageFacade imageFacade;

    @Autowired
    HtmlTextRepository htmlTextRepository;

    @Autowired
    HtmlTextMapper htmlTextMapper;

    @Autowired
    HtmlTextService htmlTextService;

    @Autowired
    HtmlTextFacade htmlTextFacade;

    @Autowired
    HtmlTextHistoryRepository htmlTextHistoryRepository;

    @Autowired
    HtmlTextHistoryMapper htmlTextHistoryMapper;

    @Autowired
    HtmlTextHistoryService htmlTextHistoryService;

    @Autowired
    HtmlTextHistoryFacade htmlTextHistoryFacade;

    @Autowired
    SettingRepository settingRepository;

    @Autowired
    SettingMapper settingMapper;

    @Autowired
    SettingService settingService;

    @Autowired
    SettingFacade settingFacade;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    AddressService addressService;

    @Autowired
    AddressFacade addressFacade;

    @Autowired
    RecoveryCodeFacade recoveryCodeFacade;

    @Autowired
    RecoveryCodeRepository recoveryCodeRepository;

//    @Autowired
//    RecoveryCodeMapper recoveryCodeMapper;
//
//    @Autowired
//    RecoveryCodeService recoveryCodeService;

}
