package com.user.model.repositories;

import com.user.model.entities.Email;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends AbstractRepository<Email, Long> {

    @Query("SELECT e FROM Email e where e.email =?1")
    Boolean existsByEmail(@Param("email") String email);

}
