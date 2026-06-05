package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    boolean existsByEmail(String email);
}