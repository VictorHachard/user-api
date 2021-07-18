package com.user.model.repositories;

import com.user.model.entities.HtmlTextHistory;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HtmlTextHistoryRepository extends AbstractRepository<HtmlTextHistory, Long> {
}
