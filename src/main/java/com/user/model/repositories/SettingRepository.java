package com.user.model.repositories;

import com.user.model.entities.Setting;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends AbstractRepository<Setting, Long> {
}
