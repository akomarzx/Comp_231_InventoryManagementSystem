package org.group4.comp231.inventorymanagementservice.repository;

import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeBookRepository extends JpaRepository<CodeBook, Long> {
}