package com.user.init;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import com.user.model.entities.*;
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
                "#272a2f",
                "#40454c",
                "#555a63",
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
                "#414750",
                "#c0c7ce",
                "#adbac7"
        ));




        HtmlText htmlText = htmlTextFacade.newInstance(
                "<p>By accessing this Website, accessible from https://ypc.yt/, you are agreeing to be bound by these Website Terms of Service and agree that you are responsible for the agreement with any applicable local laws. If you disagree with any of these terms, you are prohibited from accessing this site. The materials contained in this Website are protected by copyright and trade mark law.</p>" +
                "<h3>2. Use License</h3>" +
                "<p>Permission is granted to temporarily download one copy of the materials on YouTube Playlist Checker Website for personal, non-commercial transitory viewing only. This is the grant of a license, not a transfer of title, and under this license you may not:</p>" +
                "<ul>" +
                "<li>reproduce, duplicate, modify or copy the materials;</li>" +
                "<li>use the materials for any commercial purpose or for any public display;</li>" +
                "<li>attempt to reverse engineer any software contained on YouTube Playlist Checker Website;</li>" +
                "<li>remove any copyright or other proprietary notations from the materials;</li>" +
                "<li>transferring the materials to another person or 'mirror' the materials on any other server.</li>" +
                "</ul>" +
                "<p>This will let YouTube Playlist Checker to terminate upon violations of any of these restrictions. Upon termination, your viewing right will also be terminated and you should destroy any downloaded materials in your possession whether it is printed or electronic format.</p>" +
                "<h3>3. Disclaimer</h3>" +
                "<p>All the materials on YouTube Playlist Checker are provided 'as is'. YouTube Playlist Checker makes no warranties, may it be expressed or implied, therefore negates all other warranties. Furthermore, YouTube Playlist Checker does not make any representations concerning the accuracy or reliability of the use of the materials on its Website or otherwise relating to such materials or any sites linked to this Website.</p>" +
                "<h3>4. Limitations</h3>" +
                "<p>YouTube Playlist Checker or its suppliers will not be hold accountable for any damages that will arise with the use or inability to use the materials on YouTube Playlist Checker Website, even if YouTube Playlist Checker or an authorize representative of this Website has been notified, orally or written, of the possibility of such damage. Some jurisdiction does not allow limitations on implied warranties or limitations of liability for incidental damages, these limitations may not apply to you.</p>" +
                "<h3>5. Revisions and Errata</h3>" +
                "<p>The materials appearing on YouTube Playlist Checker Website may include technical, typographical, or photographic errors. YouTube Playlist Checker will not promise that any of the materials in this Website are accurate, complete, or current. YouTube Playlist Checker may change the materials contained on its Website at any time without notice. YouTube Playlist Checker does not make any commitment to update the materials.</p>" +
                "<h3>6. Links</h3>" +
                "<p>YouTube Playlist Checker has not reviewed all of the sites linked to its Website and is not responsible for the contents of any such linked site. The presence of any link does not imply endorsement by YouTube Playlist Checker of the site. The use of any linked website is at the user’s own risk.</p>" +
                "<h3>7. Site Terms of Use Modifications</h3>" +
                "<p>YouTube Playlist Checker may revise these Terms of Use for its Website at any time without prior notice. By using this Website, you are agreeing to be bound by the current version of these Terms and Conditions of Use.</p>" +
                "<h3>8. Your Privacy</h3>" +
                "<p>Please read our Privacy Policy.</p>" +
                "<h3>9. Governing Law</h3>" +
                "<p>Any claim related to YouTube Playlist Checker Website shall be governed by the laws of fr without regards to its conflict of law provisions.</p>"
        );
        htmlTextRepository.save(htmlText);
        HtmlTextHistory htmlTextHistory = htmlTextHistoryFacade.newInstance("terms");
        htmlTextHistory.addHtmlText(htmlText);
        htmlTextHistoryRepository.save(htmlTextHistory);

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
