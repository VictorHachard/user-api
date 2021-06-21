package com.user.model.repositories;

import com.user.model.entities.Email;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.PrivacyEnum;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends AbstractRepository<Email, Long> {

    Boolean existsByEmail(@Param("email") String email);

    Optional<Email> findByEmail(@Param("email") String email);

    @Query("SELECT case when count(e)> 0 then true else false end FROM Email e where e.email =?1 and e.priority =?2")
    Boolean existsByEmailAndPriority(@Param("email") String email, @Param("priority") PriorityEnum priority);

    Boolean existsByEmailConfirmedToken(@Param("emailConfirmedToken") String emailConfirmedToken);

    Optional<Email> findByEmailConfirmedToken(@Param("emailConfirmedToken") String emailConfirmedToken);

    Page<Email> findByEmailContaining(String email, Pageable pageable);

    Page<Email> findByPriorityContaining(PriorityEnum priorityEnum, Pageable pageable);

    Page<Email> findByPrivacyContaining(PrivacyEnum privacyEnum, Pageable pageable);

}
