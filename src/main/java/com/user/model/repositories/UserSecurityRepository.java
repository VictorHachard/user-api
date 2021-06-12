package com.user.model.repositories;

import com.user.model.entities.Email;
import com.user.model.entities.UserSecurity;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecurityRepository extends AbstractRepository<UserSecurity, Long> {

    @Query("SELECT u FROM UserSecurity u join u.emailList e where e.email =?1")
    Optional<UserSecurity> findByEmail(@Param("email") String email);

    @Query("SELECT case when count(u)> 0 then true else false end FROM UserSecurity u join u.emailList e where e.email =?1")
    Boolean existsByEmail(@Param("email") String email);

    @Query("SELECT u FROM UserSecurity u join u.cookieList c where c.token =?1")
    Optional<UserSecurity> findByCookieRemember(@Param("cookieRemember") String cookieRemember);

    @Query("SELECT case when count(u)> 0 then true else false end FROM UserSecurity u join u.cookieList c where c.token =?1")
    Boolean existsByCookieRemember(@Param("cookieRemember") String cookieRemember);

    Optional<UserSecurity> findByUsername(@Param("username") String username);

    Boolean existsByUsername(@Param("username") String username);

    Optional<UserSecurity> findByAuthToken(@Param("authToken") String authToken);

    Boolean existsByAuthToken(@Param("authToken") String authToken);

    Page<UserSecurity> findByUsernameContaining(String username, Pageable pageable);

}
