package com.user.model.repositories;

import com.user.model.entities.Email;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends AbstractRepository<Email, Long> {

    Boolean existsByEmail(@Param("email") String email);

    @Query("SELECT case when count(e)> 0 then true else false end FROM Email e where e.email =?1 and e.priority =?2")
    Boolean existsByEmailAndPriority(@Param("email") String email, @Param("priority") PriorityEnum priority);

    Boolean  existsByEmailConfirmedToken(@Param("emailConfirmedToken") String emailConfirmedToken);

}
