package com.user.model.repositories;

import com.user.model.entities.UserSecurity;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecurityRepository extends AbstractRepository<UserSecurity, Long> {

    @Query("SELECT u FROM UserSecurity u join u.emailList e where e.email =?1")
    Optional<UserSecurity> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM UserSecurity u join u.emailList e where e.email =?1")
    Boolean existsByEmail(@Param("email") String email);

    Optional<UserSecurity> findByUsername(@Param("username") String username);

    Boolean existsByUsername(@Param("username") String username);

    Optional<UserSecurity> findByAuthToken(@Param("authToken") String authToken);

    Boolean existsByAuthToken(@Param("authToken") String authToken);

}
