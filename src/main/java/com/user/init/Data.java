package com.user.init;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import com.user.model.entities.Email;
import com.user.model.entities.Password;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.PrivacyEnum;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.omnifaces.cdi.Startup;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Startup
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Data extends AbstractAutowire {

    Lorem lorem = LoremIpsum.getInstance();

    @PostConstruct
    public void init() {

        themeRepository.save(themeFacade.newInstance(
                "Default light",
                true,
                0,
                "#ffffff",
                "#f1f1f1",
                "#eaeaea",
                "#e3e3e3",
                "#000000",
                "#1a1a1a"
        ));
        themeRepository.save(themeFacade.newInstance(
                "Light dimmed",
                true,
                1,
                "#f1f1f1",
                "#eaeaea",
                "#e3e3e3",
                "#d4d4d4",
                "#1a1a1a",
                "#3b3b3b"
        ));
        themeRepository.save(themeFacade.newInstance(
                "Default dark",
                true,
                2,
                "#0a0c10",
                "#272b33",
                "#606873",
                "#7a828e",
                "#ffffff",
                "#edf2f7"
        ));
        themeRepository.save(themeFacade.newInstance(
                "Default dark blue",
                true,
                3,
                "#0d1117",
                "#161b22",
                "#21262f",
                "#30363d",
                "#eeeeee",
                "#d4d4d4"
        ));
        themeRepository.save(themeFacade.newInstance(
                "Dark dimmed",
                true,
                4,
                "#202225",
                "#292b2f",
                "#2f3136",
                "#36393f",
                "#ececec",
                "#c1c1c1"
        ));
        themeRepository.save(themeFacade.newInstance(
                "Dark blue dimmed",
                true,
                5,
                "#22272e",
                "#2d333b",
                "#373e47",
                "#3b4048",
                "#c0c7ce",
                "#adbac7"
        ));

        for (int i = 0; i < 50; i++) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Password p = passwordFacade.newInstance(passwordEncoder.encode("Test123*"));
            Email e = emailFacade.newInstance(lorem.getEmail(), PriorityEnum.PRIMARY, PrivacyEnum.PRIVATE);
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
