package com.user.model.repositories;

import com.user.model.entities.Country;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends AbstractRepository<Country, Long> {

    Boolean existsByName(String name);

    Optional<Country> findByName(String name);

    @Query("select c from Country c where LOWER(c.name) like %?1%")
    Page<Country> findByNameContaining(String name, Pageable pageable);

}
