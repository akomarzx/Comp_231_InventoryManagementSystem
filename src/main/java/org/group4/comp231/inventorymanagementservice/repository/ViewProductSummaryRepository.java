package org.group4.comp231.inventorymanagementservice.repository;

import org.group4.comp231.inventorymanagementservice.domain.ViewProductSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ViewProductSummaryRepository extends JpaRepositoryImplementation<ViewProductSummary, Long> {
    public Page<ViewProductSummary> findAllBy(Pageable pageable);
}