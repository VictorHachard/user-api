package com.user.model.repositories;

import com.user.model.entities.CookieRemember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookieRememberRepository extends JpaRepository<CookieRemember, Long> {
}
