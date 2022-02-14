package com.user.init;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import com.user.Environment;
import com.user.model.entities.*;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.PrivacyEnum;
import com.user.model.entities.enums.RoleEnum;
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

        countryRepository.save(countryFacade.newInstance("France"));
        countryRepository.save(countryFacade.newInstance("Belgium"));

        roleRepository.save(roleFacade.newInstance(RoleEnum.ROLE_USER));
        roleRepository.save(roleFacade.newInstance(RoleEnum.ROLE_ADMINISTRATOR));
        roleRepository.save(roleFacade.newInstance(RoleEnum.ROLE_OWNER));

        settingRepository.save(settingFacade.newInstance("profile", true, false));
        settingRepository.save(settingFacade.newInstance("account", true, false));
        settingRepository.save(settingFacade.newInstance("appearance", true, true));
        settingRepository.save(settingFacade.newInstance("security", true, false));
        settingRepository.save(settingFacade.newInstance("emails", true, false));
        settingRepository.save(settingFacade.newInstance("address", true, true));
        settingRepository.save(settingFacade.newInstance("cookies", true, true));
        settingRepository.save(settingFacade.newInstance("notifications", true, true));
        settingRepository.save(settingFacade.newInstance("security-log", true, true));
        settingRepository.save(settingFacade.newInstance("blocked-users", true, true));
        settingRepository.save(settingFacade.newInstance("interaction-limits", true, true));

        String name = Environment.getInstance().NAME;
        String domainName = Environment.getInstance().DOMAIN_NAME;
        
        HtmlText htmlText = htmlTextFacade.newInstance(
                "<h2>" + name + " Website Terms of Service</h2>" +
                "<h3>1. Terms</h3>" +
                "<p>By accessing this Website, accessible from " + domainName + ", you are agreeing to be bound by these Website Terms of Service and agree that you are responsible for the agreement with any applicable local laws. If you disagree with any of these terms, you are prohibited from accessing this site. The materials contained in this Website are protected by copyright and trade mark law.</p>" +
                "<h3>2. Use License</h3>" +
                "<p>Permission is granted to temporarily download one copy of the materials on " + name + " Website for personal, non-commercial transitory viewing only. This is the grant of a license, not a transfer of title, and under this license you may not:</p>" +
                "<ul>" +
                "<li>reproduce, duplicate, modify or copy the materials;</li>" +
                "<li>use the materials for any commercial purpose or for any public display;</li>" +
                "<li>attempt to reverse engineer any software contained on " + name + " Website;</li>" +
                "<li>remove any copyright or other proprietary notations from the materials;</li>" +
                "<li>transferring the materials to another person or 'mirror' the materials on any other server.</li>" +
                "</ul>" +
                "<p>This will let " + name + " to terminate upon violations of any of these restrictions. Upon termination, your viewing right will also be terminated and you should destroy any downloaded materials in your possession whether it is printed or electronic format.</p>" +
                "<h3>3. Disclaimer</h3>" +
                "<p>All the materials on " + name + " are provided 'as is'. " + name + " makes no warranties, may it be expressed or implied, therefore negates all other warranties. Furthermore, " + name + " does not make any representations concerning the accuracy or reliability of the use of the materials on its Website or otherwise relating to such materials or any sites linked to this Website.</p>" +
                "<h3>4. Limitations</h3>" +
                "<p>" + name + " or its suppliers will not be hold accountable for any damages that will arise with the use or inability to use the materials on " + name + " Website, even if " + name + " or an authorize representative of this Website has been notified, orally or written, of the possibility of such damage. Some jurisdiction does not allow limitations on implied warranties or limitations of liability for incidental damages, these limitations may not apply to you.</p>" +
                "<h3>5. Revisions and Errata</h3>" +
                "<p>The materials appearing on " + name + " Website may include technical, typographical, or photographic errors. " + name + " will not promise that any of the materials in this Website are accurate, complete, or current. " + name + " may change the materials contained on its Website at any time without notice. " + name + " does not make any commitment to update the materials.</p>" +
                "<h3>6. Links</h3>" +
                "<p>" + name + " has not reviewed all of the sites linked to its Website and is not responsible for the contents of any such linked site. The presence of any link does not imply endorsement by " + name + " of the site. The use of any linked website is at the user’s own risk.</p>" +
                "<h3>7. Site Terms of Use Modifications</h3>" +
                "<p>" + name + " may revise these Terms of Use for its Website at any time without prior notice. By using this Website, you are agreeing to be bound by the current version of these Terms and Conditions of Use.</p>" +
                "<h3>8. Your Privacy</h3>" +
                "<p>Please read our Privacy Policy.</p>" +
                "<h3>9. Governing Law</h3>" +
                "<p>Any claim related to " + name + " Website shall be governed by the laws of france without regards to its conflict of law provisions.</p>"
        );
        htmlTextRepository.save(htmlText);
        HtmlTextHistory htmlTextHistory = htmlTextHistoryFacade.newInstance("terms");
        htmlTextHistory.addHtmlText(htmlText);
        htmlTextHistoryRepository.save(htmlTextHistory);

        htmlText = htmlTextFacade.newInstance(
        "<h2>" + name + " Website Privacy Policy</h2>" +
                "<p>At " + name + ", accessible from " + domainName + ", one of our main priorities is the privacy of our visitors. This Privacy Policy document contains types of information that is collected and recorded by " + name + " and how we use it.</p>" +
                "<p>If you have additional questions or require more information about our Privacy Policy, do not hesitate to contact us.</p>" +
                "<p>This Privacy Policy applies only to our online activities and is valid for visitors to our website with regards to the information that they shared and/or collect in " + name + ". This policy is not applicable to any information collected offline or via channels other than this website.</p>" +
                "<h3>1. Consent</h3>" +
                "<p>By using our website, you hereby consent to our Privacy Policy and agree to its terms.</p>" +
                "<h3>2. Information we collect</h3>" +
                "<p>The personal information that you are asked to provide, and the reasons why you are asked to provide it, will be made clear to you at the point we ask you to provide your personal information.</p>" +
                "<p>If you contact us directly, we may receive additional information about you such as your name, email address, the contents of the message and/or attachments you may send us, and any other information you may choose to provide.</p>" +
                "<p>When you register for an Account, we may ask for your contact information, including items such as name, email address.</p>" +
                "<h3>3. How we use your information</h3>" +
                "<p>We use the information we collect in various ways, including to:</p>" +
                "<ul>" +
                "<li>Provide, operate, and maintain our website</li>" +
                "<li>Improve, personalize, and expand our website</li>" +
                "<li>Understand and analyze how you use our website</li>" +
                "<li>Develop new products, services, features, and functionality</li>" +
                "<li>Communicate with you, either directly or through one of our partners, including for customer service, to provide you with updates and other information relating to the website, and for marketing and promotional purposes</li>" +
                "<li>Send you emails</li>" +
                "<li>Find and prevent fraud</li>" +
                "</ul>" +
                "<h3>4. Log Files</h3>" +
                "<p>" + name + " follows a standard procedure of using log files. These files log visitors when they visit websites. All hosting companies do this and a part of hosting services' analytics. The information collected by log files include internet protocol (IP) addresses, browser type, Internet Service Provider (ISP), date and time stamp, referring/exit pages, and possibly the number of clicks. These are not linked to any information that is personally identifiable. The purpose of the information is for analyzing trends, administering the site, tracking users' movement on the website, and gathering demographic information.</p>" +
                "<h3>5. Cookies and Web Beacons</h3>" +
                "<p>Like any other website, " + name + " uses 'cookies'. These cookies are used to store information including visitors' preferences, and the pages on the website that the visitor accessed or visited. The information is used to optimize the users' experience by customizing our web page content based on visitors' browser type and/or other information.</p>" +
                "<p>For more general information on cookies, please read <a href=\"https://www.cookieconsent.com/what-are-cookies/\">\"What Are Cookies\"</a>.</p>" +
                "<h3>6. GDPR Data Protection Rights</h3>" +
                "<p>We would like to make sure you are fully aware of all of your data protection rights. Every user is entitled to the following:</p>" +
                "<ul>" +
                "<li>The right to access – You have the right to request copies of your personal data. We may charge you a small fee for this service.</li>" +
                "<li>The right to rectification – You have the right to request that we correct any information you believe is inaccurate. You also have the right to request that we complete the information you believe is incomplete.</li>" +
                "<li>The right to erasure – You have the right to request that we erase your personal data, under certain conditions.</li>" +
                "<li>The right to restrict processing – You have the right to request that we restrict the processing of your personal data, under certain conditions.</li>" +
                "<li>The right to object to processing – You have the right to object to our processing of your personal data, under certain conditions.</li>" +
                "<li>The right to data portability – You have the right to request that we transfer the data that we have collected to another organization, or directly to you, under certain conditions.</li>" +
                "</ul>" +
                "<p>If you make a request, we have one month to respond to you. If you would like to exercise any of these rights, please contact us.</p>" +
                "<h3>7. Children's Information</h3>" +
                "<p>Another part of our priority is adding protection for children while using the internet. We encourage parents and guardians to observe, participate in, and/or monitor and guide their online activity.</p>" +
                "<p>" + name + " does not knowingly collect any Personal Identifiable Information from children under the age of 13. If you think that your child provided this kind of information on our website, we strongly encourage you to contact us immediately and we will do our best efforts to promptly remove such information from our records.</p>"
        );
        htmlTextRepository.save(htmlText);
        htmlTextHistory = htmlTextHistoryFacade.newInstance("privacy");
        htmlTextHistory.addHtmlText(htmlText);
        htmlTextHistoryRepository.save(htmlTextHistory);

        for (int i = 0; i < 5; i++) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Password p = passwordFacade.newInstance(passwordEncoder.encode("Test123*"));
            Email e = emailFacade.newInstance(lorem.getEmail(), PriorityEnum.PRIMARY, PrivacyEnum.PRIVATE);
            emailFacade.initToken(e);
            String un;
            do { un = lorem.getFirstName(); } while (userSecurityRepository.existsByUsername(un));
            Role r = roleRepository.findByRole(RoleEnum.ROLE_USER).get();
            UserSecurity u = userSecurityFacade.newInstance(e, p, r, un);
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
