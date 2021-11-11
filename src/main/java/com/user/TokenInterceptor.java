package com.user;

import com.user.model.entities.Role;
import com.user.model.entities.Session;
import com.user.model.entities.UserSecurity;
import com.user.model.entities.enums.RoleEnum;
import com.user.model.repositories.SessionRepository;
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
import java.util.Arrays;
import java.util.Date;

public class TokenInterceptor extends HandlerInterceptorAdapter {

    public static UserSecurity userSecurity = null;

    public static Session userSession = null;

    @Autowired
    UserSecurityRepository userSecurityRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println(handler);
        String authToken = request.getHeader("Authorization");
        Method handlerMethod = null;
        try {
            handlerMethod = this.hasAuthorizedAnnotation(handler.toString());
        } catch (Exception e) {
            System.out.println("Not Found");
            return true;
        }

        userSecurity = null; //TODO Better understand why the value is not null
        userSession = null;
        if (handlerMethod != null && handlerMethod.isAnnotationPresent(Authorisation.class)) {

            if (authToken != null) {
                String hashAuthToken = Utils.hash256(authToken);
                if (!this.userSecurityRepository.existsByAuthToken(hashAuthToken)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The token is not correct");
                }
                UserSecurity user = this.userSecurityRepository.findByAuthToken(hashAuthToken).get();
                Session session = this.sessionRepository.findByAuthToken(hashAuthToken).get();
                if (session.getAuthTokenCreatedAt().before(new Date(new Date().getTime() - 1l * 24 * 60 * 60 * 1000))) { //24h
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The token is expired");
                }
                Authorisation annotation = handlerMethod.getAnnotation(Authorisation.class);

                boolean hasPermission = false;
                for (RoleEnum r : annotation.roles()) {
                    for (Role role : user.getPermissionList()) {
                        if (role.getRole().equals(r)) {
                            hasPermission = true;
                            break;
                        }
                    }
                }
                if (!hasPermission) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The user has not permission");
                }
                userSecurity = user;
                userSession = session;
                return true;
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This access need an authentication token");
            }
        }
        return true;
    }

    /**
     *
     * @param handler like "com.user.controller.UserController#getUser(String)"
     * @return the method
     * @throws Exception
     */
    private Method hasAuthorizedAnnotation(String handler) throws Exception {
        Class<?> cls = null;
        try {
            cls = Class.forName(handler.split("#")[0]);
            Method thisIsTheOne = null;
            // There is no argument
            if (handler.split("#")[1].split("\\(")[1].replaceFirst(".$","").equals("")) {
                thisIsTheOne = cls.getMethod(handler.split("#")[1].split("\\(")[0], null);
            } else { //There is an argument
                String[] parameterNameList = handler.split("#")[1].split("\\(")[1].replaceFirst(".$","").split(",");
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
            } else {
                return thisIsTheOne;
            }
        } catch (ClassNotFoundException e) {
            throw e;
        }
    }

}
