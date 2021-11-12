package com.user.model.repositories;

import com.user.model.entities.RecoveryCode;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryCodeRepository extends AbstractRepository<RecoveryCode, Long> {

}
