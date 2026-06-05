package com.library.library_management_system.repository;

import com.library.library_management_system.entity.BorrowingTransaction;
import com.library.library_management_system.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {
    List<BorrowingTransaction> findByBorrowerId(Long borrowerId);
    List<BorrowingTransaction> findByBookId(Long bookId);
    long countByBorrowerIdAndStatus(Long borrowerId, TransactionStatus status);
}