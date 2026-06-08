package com.library.library_management_system.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BorrowingTransactionRequestDTO {

    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be a positive number")
    private Long bookId;

    @NotNull(message = "Borrower ID is required")
    @Positive(message = "Borrower ID must be a positive number")
    private Long borrowerId;
}