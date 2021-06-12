package com.user.init;

import com.user.model.entities.Email;
import com.user.model.entities.Group;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.omnifaces.cdi.Startup;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Startup
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Data extends AbstractAutowire {

    static List<Email> emailList = new ArrayList<>();
    static List<Group> groupList = new ArrayList<>();

    @PostConstruct
    public void init() {

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
