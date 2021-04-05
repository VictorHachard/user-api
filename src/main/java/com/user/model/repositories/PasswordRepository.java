package com.user.model.repositories;

import com.user.model.entities.Password;
import com.user.model.entities.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
}
