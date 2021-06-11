package com.user.init;

import com.user.model.repositories.commons.AbstractRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.omnifaces.cdi.Startup;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
@Startup
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class InitRepository extends AbstractAutowire {

    public static Map<String, AbstractRepository> map = new HashMap<>();

    @PostConstruct
    public void init() throws IllegalAccessException {
        for (Field f : AbstractAutowire.class.getDeclaredFields()) {
            if (f.getName().contains("Repository")) {
                map.put(f.getName().replace("Repository", ""), (AbstractRepository) f.get(this));
            }
        }
    }

    public static <T>T get(Class c) {
        return (T) map.get(c.getSimpleName().replace("Service", "").toLowerCase());
    }

}