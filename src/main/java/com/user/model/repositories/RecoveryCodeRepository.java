package com.user.model.repositories;

import com.user.model.entities.RecoveryCode;
import com.user.model.entities.Role;
import com.user.model.entities.enums.RoleEnum;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecoveryCodeRepository extends AbstractRepository<RecoveryCode, Long> {

}
