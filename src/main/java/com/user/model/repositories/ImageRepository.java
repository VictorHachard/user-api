package com.user.model.repositories;

import com.user.model.entities.Image;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends AbstractRepository<Image, Long> {
}
