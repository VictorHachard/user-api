package com.user.model.repositories;

import com.user.model.entities.HtmlText;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HtmlTextRepository extends AbstractRepository<HtmlText, Long> {
}
