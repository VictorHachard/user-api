package com.user.model.repositories;

import com.user.model.entities.Address;
import com.user.model.entities.Country;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends AbstractRepository<Country, Long> {

    Page<Country> findByCountryContaining(String name, Pageable pageable);

}
