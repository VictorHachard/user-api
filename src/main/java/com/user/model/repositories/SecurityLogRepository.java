package com.user.model.repositories;

import com.user.model.entities.SecurityLog;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityLogRepository extends AbstractRepository<SecurityLog, Long> {

    @Query(value = "SELECT s FROM SecurityLog s WHERE s.userSecurity.id =?1")
    Page<SecurityLog> findByUserSecurityId(Long userSecurityId, Pageable pageable);

}
