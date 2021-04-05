package com.user.model.repositories;

import com.user.model.entities.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity, Long> {

    @Query("SELECT u FROM UserSecurity u join u.emailList e where e.email =?1")
    Boolean existsByEmail(@Param("email") String email);

    Boolean existsByUsername(@Param("username") String username);

}
