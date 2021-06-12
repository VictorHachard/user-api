package com.user.init;

import com.user.mapper.commons.AbstractMapper;
import com.user.model.repositories.commons.AbstractRepository;
import com.user.service.commons.AbstractService;
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
public class InitMap extends AbstractAutowire {

    private static final Map<String, AbstractMapper> mapMapper = new HashMap<>();
    private static final Map<String, AbstractRepository> mapRepository = new HashMap<>();
    private static final Map<String, AbstractService> mapService = new HashMap<>();

    @PostConstruct
    public void init() throws IllegalAccessException {
        for (Field f : AbstractAutowire.class.getDeclaredFields()) {
            if (f.getName().contains("Service")) {
                mapService.put(f.getName().replace("Service", "").toLowerCase(), (AbstractService) f.get(this));
            } else if (f.getName().contains("Mapper")) {
                mapMapper.put(f.getName().replace("Mapper", "").toLowerCase(), (AbstractMapper) f.get(this));
            } else if (f.getName().contains("Repository")) {
                mapRepository.put(f.getName().replace("Repository", "").toLowerCase(), (AbstractRepository) f.get(this));
            }
        }
    }

    public static <T>T get(Class c, MapTypeEnum mt) {
        String s = c.getSimpleName()
                .replace("Controller", "")
                .replace("Repository", "")
                .replace("Service", "")
                .toLowerCase();
        if (mt == MapTypeEnum.SERVICE) {
            return (T) mapService.get(s);
        } else if (mt == MapTypeEnum.MAPPER) {
            return (T) mapMapper.get(s);
        } else if (mt == MapTypeEnum.REPOSITORY) {
            return (T) mapRepository.get(s);
        } else {
            return null;
        }
    }

}