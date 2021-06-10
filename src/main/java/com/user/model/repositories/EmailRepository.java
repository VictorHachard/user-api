package com.user.model.repositories;

import com.user.model.entities.Email;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends AbstractRepository<Email, Long> {

}
