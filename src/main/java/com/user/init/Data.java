package com.user.init;

import com.user.model.entities.Email;
import com.user.model.entities.PriorityEnum;
import com.user.model.facades.EmailFacade;
import com.user.model.repositories.EmailRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.omnifaces.cdi.Startup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Startup
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Data {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    EmailFacade emailFacade;

    static List<Email> emailList = new ArrayList<>();

    @PostConstruct
    public void init() {
        emailList.add(emailFacade.newInstance("Test@@test.test", PriorityEnum.PRINCIPAL));

        emailList.forEach(a -> {
            emailRepository.save(a);
        });
    }
}
