package com.user.init;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import com.user.model.entities.Email;
import com.user.model.entities.Group;
import com.user.model.entities.Password;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.PrivacyEnum;
import com.user.utils.Utils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.omnifaces.cdi.Startup;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Startup
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Data extends AbstractAutowire {

    Lorem lorem = LoremIpsum.getInstance();

    @PostConstruct
    public void init() {

        themeRepository.save(themeFacade.newInstance("Default light", true, 0));
        themeRepository.save(themeFacade.newInstance("Default dark", true, 0));
        themeRepository.save(themeFacade.newInstance("Dark dimmed", true, 0));

        for (int i = 0; i < 50; i++) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Password p = passwordFacade.newInstance(passwordEncoder.encode("Test123*"));
            Email e = emailFacade.newInstance(lorem.getEmail(), PriorityEnum.PRINCIPAL, PrivacyEnum.PRIVATE);
            emailFacade.initToken(e);
            String un;
            do { un = lorem.getFirstName(); } while (userSecurityRepository.existsByUsername(un));
            UserSecurity u = userSecurityFacade.newInstance(e, p, un);
            u.setFirstName(lorem.getFirstName());
            u.setLastName(lorem.getLastName());
            u.setMiddleName("");
            passwordRepository.save(p);
            emailRepository.save(e);
            userSecurityRepository.save(u);
        }

        /*emailList.add(emailFacade.newInstance("test@test.test", PriorityEnum.PRINCIPAL));*/

        /*emailList.forEach(a -> {
            emailRepository.save(a);
        });

        groupList.add(groupFacade.newInstance("Admin"));
        groupList.add(groupFacade.newInstance("Aadmin"));

        groupList.forEach(a -> {
            groupRepository.save(a);
        });*/
    }

}
