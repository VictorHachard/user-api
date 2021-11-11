package com.user.model.repositories;

import com.user.model.entities.Session;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends AbstractRepository<Session, Long> {

    Boolean existsByToken(@Param("token") String token);

    Optional<Session> findByToken(@Param("token") String token);

    @Query("SELECT s FROM Session s where s.authToken =?1")
    Optional<Session> findByAuthToken(@Param("authToken") String authToken);

    @Query("SELECT case when count(s)> 0 then true else false end FROM Session s where s.authToken =?1")
    Boolean existsByAuthToken(@Param("authToken") String authToken);

}
