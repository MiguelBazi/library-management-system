package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.BorrowingTransactionRequestDTO;
import com.library.library_management_system.dto.response.BorrowingTransactionResponseDTO;
import org.springframework.data.domain.Page;
import java.util.List;

public interface IBorrowingTransactionService {
    BorrowingTransactionResponseDTO borrowBook(BorrowingTransactionRequestDTO dto);
    BorrowingTransactionResponseDTO returnBook(Long transactionId);
    Page<BorrowingTransactionResponseDTO> getAllTransactions(int page, int size);
    List<BorrowingTransactionResponseDTO> getTransactionsByBorrower(Long borrowerId);
}