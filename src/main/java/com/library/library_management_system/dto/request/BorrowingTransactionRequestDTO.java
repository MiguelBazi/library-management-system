package com.library.library_management_system.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowingTransactionRequestDTO {

    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Borrower ID is required")
    private Long borrowerId;
}