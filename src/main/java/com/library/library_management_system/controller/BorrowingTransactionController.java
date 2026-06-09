package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.BorrowingTransactionRequestDTO;
import com.library.library_management_system.dto.response.ApiResponse;
import com.library.library_management_system.dto.response.BorrowingTransactionResponseDTO;
import com.library.library_management_system.service.BorrowingTransactionService;
import com.library.library_management_system.service.IBorrowingTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class BorrowingTransactionController {

    private final IBorrowingTransactionService transactionService;

    @PostMapping("/borrow")
    public ResponseEntity<ApiResponse<BorrowingTransactionResponseDTO>> borrowBook(@Valid @RequestBody BorrowingTransactionRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Book borrowed successfully", transactionService.borrowBook(dto)));
    }

    @PutMapping("/return/{transactionId}")
    public ResponseEntity<ApiResponse<BorrowingTransactionResponseDTO>> returnBook(@PathVariable Long transactionId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Book returned successfully", transactionService.returnBook(transactionId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BorrowingTransactionResponseDTO>>> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Transactions retrieved successfully", transactionService.getAllTransactions(page, size)));
    }

    @GetMapping("/borrower/{borrowerId}")
    public ResponseEntity<ApiResponse<List<BorrowingTransactionResponseDTO>>> getTransactionsByBorrower(@PathVariable Long borrowerId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Transactions retrieved successfully", transactionService.getTransactionsByBorrower(borrowerId)));
    }
}