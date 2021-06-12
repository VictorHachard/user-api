package com.user.init;

import com.user.model.entities.Email;
import com.user.model.entities.Group;
import com.user.model.entities.enums.PriorityEnum;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.omnifaces.cdi.Startup;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InitPostConstruct extends AbstractAutowire {

}
