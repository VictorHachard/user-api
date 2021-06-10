package com.user.init;

import com.user.mapper.commons.AbstractMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.omnifaces.cdi.Startup;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Startup
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class InitMapper extends AbstractAutowire {

    public static Map<String, AbstractMapper> map = new HashMap<>();

    @PostConstruct
    public void init() {
        map.put("Email", emailMapper);
        /*try {
            List<Class> allClasses = Utils.getClasses("com.user.model.repositories.app");
            for (Class clazz : allClasses) {
                if (!repositoryMap.containsKey(clazz.getSimpleName().replace("MApper", ""))) {
                    log.warning(clazz.getSimpleName() + " is missing in the repositoryMap");
                }
            }
        } catch (Exception ignored) { }*/
    }

    public static <T>T get(Class c) {
        return (T) map.get(c.getSimpleName().replace("Service", ""));
    }

}