package com.library.library_management_system.repository;

import com.library.library_management_system.entity.BorrowingTransaction;
import com.library.library_management_system.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {
    List<BorrowingTransaction> findByBorrowerId(Long borrowerId);
    List<BorrowingTransaction> findByBookId(Long bookId);
    long countByBorrowerIdAndStatus(Long borrowerId, TransactionStatus status);
}