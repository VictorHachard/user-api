package com.user.model.repositories;

import com.user.model.entities.Address;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends AbstractRepository<Address, Long> {

    Page<Address> findByNameContaining(String name, Pageable pageable);

    Page<Address> findByAliasContaining(String alias, Pageable pageable);

}
