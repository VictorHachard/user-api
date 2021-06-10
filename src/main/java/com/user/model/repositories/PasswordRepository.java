package com.user.model.repositories;

import com.user.model.entities.Password;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends AbstractRepository<Password, Long> {
}
