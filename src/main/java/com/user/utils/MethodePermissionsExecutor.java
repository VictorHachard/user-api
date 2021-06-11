package com.user.utils;

import com.user.controller.commons.AbstractController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.omnifaces.cdi.Startup;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
@Startup
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class MethodePermissionsExecutor {

    @PostConstruct
    public void executeMethod() {
        System.out.println("----> executeMethod");
        Method[] methods = AbstractController.class.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Permissions) {
                    System.out.println("Annotation: " + annotation);
                }
            }
        }
    }

}
