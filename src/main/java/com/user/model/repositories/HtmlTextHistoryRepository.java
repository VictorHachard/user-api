package com.user.model.repositories;

import com.user.model.entities.HtmlTextHistory;
import com.user.model.repositories.commons.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface HtmlTextHistoryRepository extends AbstractRepository<HtmlTextHistory, Long> {

    Page<HtmlTextHistory> findByNameContaining(String name, Pageable pageable);

}
