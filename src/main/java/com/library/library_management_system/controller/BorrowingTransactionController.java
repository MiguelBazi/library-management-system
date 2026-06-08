package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.BorrowingTransactionRequestDTO;
import com.library.library_management_system.dto.response.ApiResponse;
import com.library.library_management_system.dto.response.BorrowingTransactionResponseDTO;
import com.library.library_management_system.service.BorrowingTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class BorrowingTransactionController {

    private final BorrowingTransactionService transactionService;

    @PostMapping("/borrow")
    public ResponseEntity<ApiResponse<BorrowingTransactionResponseDTO>> borrowBook(@Valid @RequestBody BorrowingTransactionRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Book borrowed successfully", transactionService.borrowBook(dto)));
    }

    @PutMapping("/return/{transactionId}")
    public ResponseEntity<ApiResponse<BorrowingTransactionResponseDTO>> returnBook(@PathVariable Long transactionId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Book returned successfully", transactionService.returnBook(transactionId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BorrowingTransactionResponseDTO>>> getAllTransactions() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Transactions retrieved successfully", transactionService.getAllTransactions()));
    }

    @GetMapping("/borrower/{borrowerId}")
    public ResponseEntity<ApiResponse<List<BorrowingTransactionResponseDTO>>> getTransactionsByBorrower(@PathVariable Long borrowerId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Transactions retrieved successfully", transactionService.getTransactionsByBorrower(borrowerId)));
    }
}