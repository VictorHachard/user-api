package com.user.model.repositories;

import com.user.model.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Boolean existsByName(@Param("name") String name);

}
