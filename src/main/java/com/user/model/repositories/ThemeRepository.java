package com.user.model.repositories;

import com.user.model.entities.Theme;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends AbstractRepository<Theme, Long> {
}