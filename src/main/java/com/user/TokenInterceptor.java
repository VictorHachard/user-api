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

public class TokenInterceptor extends HandlerInterceptorAdapter {

    public static UserSecurity userSecurity = null;

    @Autowired
    UserSecurityRepository userSecurityRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authToken = request.getHeader("Authorization");
        String hashAuthToken = Utils.hash256(authToken);
        if (authToken != null) {
            if (!this.userSecurityRepository.existsByAuthToken(hashAuthToken)) {
                //TODO check date
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The token is not correct");
            }
            userSecurity = this.userSecurityRepository.findByAuthToken(hashAuthToken).get();
        }
        return true;
    }

}
