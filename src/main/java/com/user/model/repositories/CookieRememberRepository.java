package com.user.model.repositories;

import com.user.model.entities.CookieRemember;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CookieRememberRepository extends AbstractRepository<CookieRemember, Long> {

    Boolean existsByToken(@Param("token") String token);

}
