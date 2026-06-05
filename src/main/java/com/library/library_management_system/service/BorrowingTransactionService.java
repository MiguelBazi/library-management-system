package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.BorrowingTransactionRequestDTO;
import com.library.library_management_system.dto.response.BorrowingTransactionResponseDTO;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.entity.Borrower;
import com.library.library_management_system.entity.BorrowingTransaction;
import com.library.library_management_system.enums.TransactionStatus;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.BorrowerRepository;
import com.library.library_management_system.repository.BorrowingTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BorrowingTransactionService {

    private final BorrowingTransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;

    @Value("${borrower.transaction.limit}")
    private int transactionLimit;

    @Transactional
    public BorrowingTransactionResponseDTO borrowBook(BorrowingTransactionRequestDTO dto) {
        log.info("Borrowing book with id: {} for borrower id: {}", dto.getBookId(), dto.getBorrowerId());

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + dto.getBookId()));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available for borrowing");
        }

        Borrower borrower = borrowerRepository.findById(dto.getBorrowerId())
                .orElseThrow(() -> new RuntimeException("Borrower not found with id: " + dto.getBorrowerId()));

        // Check transaction limit
        long activeTransactions = transactionRepository
                .countByBorrowerIdAndStatus(dto.getBorrowerId(), TransactionStatus.BORROWED);
        log.info("Borrower {} has {} active transactions, limit is {}",
                dto.getBorrowerId(), activeTransactions, transactionLimit);

        if (activeTransactions >= transactionLimit) {
            throw new RuntimeException("Borrower has reached the maximum limit of "
                    + transactionLimit + " borrowed books");
        }

        // Mark book as unavailable
        book.setAvailable(false);
        bookRepository.save(book);

        // Create transaction
        BorrowingTransaction transaction = new BorrowingTransaction();
        transaction.setBook(book);
        transaction.setBorrower(borrower);
        transaction.setBorrowDate(LocalDate.now());
        transaction.setStatus(TransactionStatus.BORROWED);

        return mapToDTO(transactionRepository.save(transaction));
    }

    @Transactional
    public BorrowingTransactionResponseDTO returnBook(Long transactionId) {
        log.info("Returning book for transaction id: {}", transactionId);

        BorrowingTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));

        if (transaction.getStatus() == TransactionStatus.RETURNED) {
            throw new RuntimeException("Book has already been returned");
        }

        // Mark book as available again
        Book book = transaction.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        // Update transaction
        transaction.setReturnDate(LocalDate.now());
        transaction.setStatus(TransactionStatus.RETURNED);

        return mapToDTO(transactionRepository.save(transaction));
    }

    public List<BorrowingTransactionResponseDTO> getAllTransactions() {
        log.info("Fetching all transactions");
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<BorrowingTransactionResponseDTO> getTransactionsByBorrower(Long borrowerId) {
        log.info("Fetching transactions for borrower id: {}", borrowerId);
        return transactionRepository.findByBorrowerId(borrowerId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private BorrowingTransactionResponseDTO mapToDTO(BorrowingTransaction transaction) {
        BorrowingTransactionResponseDTO dto = new BorrowingTransactionResponseDTO();
        dto.setId(transaction.getId());
        dto.setBookTitle(transaction.getBook().getTitle());
        dto.setBorrowerName(transaction.getBorrower().getName());
        dto.setBorrowDate(transaction.getBorrowDate());
        dto.setReturnDate(transaction.getReturnDate());
        dto.setStatus(transaction.getStatus());
        return dto;
    }
}