package com.user.model.repositories;

import com.user.model.entities.Session;
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

    @Query("SELECT case when count(u)> 0 then true else false end FROM UserSecurity u JOIN u.emailList e where e.email =?1 or u.username =?1")
    Boolean existsByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);

    @Query("SELECT u FROM UserSecurity u JOIN u.emailList e where e.email =?1 or u.username =?1")
    Optional<UserSecurity> findByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);

    Boolean existsByPasswordResetToken(@Param("passwordResetToken") String passwordResetToken);

    Optional<UserSecurity> findByPasswordResetToken(@Param("passwordResetToken") String passwordResetToken);

    @Query("SELECT u FROM UserSecurity u join u.emailList e where e.email =?1")
    Optional<UserSecurity> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM UserSecurity u join u.emailList e where e.id =?1")
    Optional<UserSecurity> findByEmailId(@Param("emailId") long emailId);

    @Query("SELECT case when count(u)> 0 then true else false end FROM UserSecurity u join u.emailList e where e.email =?1")
    Boolean existsByEmail(@Param("email") String email);

    @Query("SELECT case when count(u)> 0 then true else false end FROM UserSecurity u join u.emailList e where e.id =?1")
    Boolean existsByEmailId(@Param("emailId") long emailId);

    Optional<UserSecurity> findByUsername(@Param("username") String username);

    Boolean existsByUsername(@Param("username") String username);

    @Query("SELECT u FROM UserSecurity u join u.sessionList s where s =?1")
    Optional<UserSecurity> findBySession(@Param("session") Session session);

    @Query("SELECT case when count(u)> 0 then true else false end FROM UserSecurity u join u.sessionList s where s =?1")
    Boolean existsBySession(@Param("session") Session session);

    @Query("SELECT u FROM UserSecurity u join u.sessionList s where s.authToken =?1")
    Optional<UserSecurity> findByAuthToken(@Param("authToken") String authToken);

    @Query("SELECT case when count(u)> 0 then true else false end FROM UserSecurity u join u.sessionList s where s.authToken =?1")
    Boolean existsByAuthToken(@Param("authToken") String authToken);

    Page<UserSecurity> findByUsernameContaining(String username, Pageable pageable);

}
