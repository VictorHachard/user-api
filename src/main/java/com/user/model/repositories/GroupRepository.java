package com.user.model.repositories;

import com.user.model.entities.Group;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends AbstractRepository<Group, Long> {

    Boolean existsByName(@Param("name") String name);

}
