package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    boolean existsByEmail(String email);
}