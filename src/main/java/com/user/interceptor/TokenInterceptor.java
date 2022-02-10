package com.user.interceptor;

import com.user.Environment;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class TokenInterceptor extends HandlerInterceptorAdapter {

    public static UserSecurity userSecurity = null;
    public static Session userSession = null;

    public static String ip = null;

    @Autowired
    UserSecurityRepository userSecurityRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Reset attributes
        userSecurity = null; //TODO Better understand why the value is not null
        userSession = null;
        ip = null;

//        Enumeration<String> headerNames = request.getHeaderNames();
//        if (headerNames != null) {
//            while (headerNames.hasMoreElements()) {
//                String headerName = headerNames.nextElement();
//                System.out.println("Header: " + headerName + " - " + request.getHeader(headerName));
//            }
//        }

        ip = Utils.getClientIp(request);

        String authToken = request.getHeader("Authorization");
        Method handlerMethod = null;
        Class<?> handlerClass = null;
        try {
            handlerClass = Class.forName(handler.toString().split("#")[0]);
            handlerMethod = this.foundTheMethode(handler.toString());
        } catch (Exception e) {
            System.out.println("Not Found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The API path was not found");
        }

        RoleEnum[] roles = this.getRoles(handlerClass, handlerMethod);
        System.out.println("Roles: " + roles);

        if (roles != null && roles.length > 0) {
            if (authToken != null) {
                String hashAuthToken = Utils.hash256(authToken);
                if (!this.userSecurityRepository.existsByAuthToken(hashAuthToken)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The token is not correct");
                }
                UserSecurity user = this.userSecurityRepository.findByAuthToken(hashAuthToken).get();
                Session session = this.sessionRepository.findByAuthToken(hashAuthToken).get();
                if (!session.getRememberMe() && session.getAuthTokenCreatedAt().before(new Date(new Date().getTime() - Environment.getInstance().SESSION_TIMEOUT))) { //24h
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The token is expired");
                } else if (session.getRememberMe() && session.getAuthTokenCreatedAt().before(new Date(new Date().getTime() - Environment.getInstance().SESSION_REMEMBER_ME_TIMEOUT))) { //30 days
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The token is expired");
                }

                System.out.println(this.userHasPermission(roles, user.getPermissionList()));

                if (!this.userHasPermission(roles, user.getPermissionList())) {
                    System.out.println("The user has not permission");
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The user has not permission");
                }
                session.setLastConnection(new Timestamp(System.currentTimeMillis()));
                sessionRepository.save(session);
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
     * Check if the user has the permission
     * @param rolesList
     * @param roleSet
     * @return
     */
    private boolean userHasPermission(RoleEnum[] rolesList, Set<Role> roleSet) {
        boolean hasPermission = false;
        for (RoleEnum r : rolesList) {
            for (Role role : roleSet) {
                if (role.getRole().equals(r)) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }

    /**
     * This methode return the role of the methode.
     * If the methode is not annotated, return role.
     * If the methode is in the abstract, return the role of the class.
     * @param handlerClass
     * @param handlerMethod
     * @return
     */
    private RoleEnum[] getRoles(Class<?> handlerClass, Method handlerMethod) {
        RoleEnum[] roles = null;
        // Check if the user has the right to access the method (if the method has the right annotation)
        if (handlerMethod.isAnnotationPresent(Authorisation.class)) {
            return handlerMethod.getAnnotation(Authorisation.class).roles();
        } else if (handlerClass.isAnnotationPresent(AuthorisationForOverrideColumn.class)) { // Check if the user has the right to access the method in the abstract (if the class has the right annotation)
            for (AuthorisationForOverride a : handlerClass.getAnnotation(AuthorisationForOverrideColumn.class).table()) {
                if (Objects.equals(a.name(), handlerMethod.getName())) {
                    return a.roles();
                }
            }
        }
        return null;
    }

    /**
     *
     * @param handler like "com.user.controller.UserController#getUser(String)"
     * @return the method
     * @throws Exception
     */
    private Method foundTheMethode(String handler) throws ClassNotFoundException, NoSuchMethodException {
        System.out.println("Handler: " + handler);
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
                throw new NoSuchMethodException("The method " + handler + " is not found");
            }
            return thisIsTheOne;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw e;
        }
    }

}
