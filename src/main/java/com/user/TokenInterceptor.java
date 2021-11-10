package com.user;

import com.user.model.entities.UserSecurity;
import com.user.model.repositories.UserSecurityRepository;
import com.user.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;

public class TokenInterceptor extends HandlerInterceptorAdapter {

    public static UserSecurity userSecurity = null;

    @Autowired
    UserSecurityRepository userSecurityRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println(handler);
        String authToken = request.getHeader("Authorization");
        boolean hasAuthorizedAnnotation;
        try {
            hasAuthorizedAnnotation = this.hasAuthorizedAnnotation(handler.toString());
        } catch (Exception e) {
            System.out.println("Not Found");
            return true;
        }

        userSecurity = null; //TODO Better understand why the value is not null
        if (hasAuthorizedAnnotation) {
            if (authToken != null) {
                String hashAuthToken = Utils.hash256(authToken);
                if (!this.userSecurityRepository.existsByAuthToken(hashAuthToken)) {
                    //TODO check date

                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The token is not correct");
                }
                UserSecurity user = this.userSecurityRepository.findByAuthToken(hashAuthToken).get();
                if (user.getAuthTokenCreatedAt().before(new Date(new Date().getTime() - 1l * 24 * 60 * 60 * 1000))) { //24h
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The token is expired");
                }
                userSecurity = user;
                return true;
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This access need an authentication token");
            }
        }
        return true;
    }

    private boolean hasAuthorizedAnnotation(String handler) throws Exception {
        Class<?> cls = null;
        try {
            cls = Class.forName(handler.split("#")[0]);
            String[] parameterNameList = handler.split("#")[1].split("\\(")[1].replaceFirst(".$","").split(",");
            Method thisIsTheOne = null;
            if (parameterNameList.length == 1 && parameterNameList[0].equals("")) {
                thisIsTheOne = cls.getMethod(handler.split("#")[1].split("\\(")[0], null);
            } else {
                for (Method m : cls.getMethods()) {
                    if (m.getName().equals(handler.split("#")[1].split("\\(")[0])) {
                        int i = 0;
                        if (m.getParameterCount() != parameterNameList.length) {
                            continue;
                        }
                        for (Parameter p : m.getParameters()) {
                            if (!p.getName().equals(parameterNameList[i])) {
                                continue;
                            }
                            i++;
                        }
                        thisIsTheOne = m;
                    }
                }
            }
            if (thisIsTheOne == null) {
                throw new Exception("Method not found");
            } else if (thisIsTheOne.isAnnotationPresent(Authorisation.class)) {
                return true;
            } else {
                return false;
            }
        } catch (ClassNotFoundException e) {
            throw e;
        }
    }

}
