package com.library.library_management_system.dto.response;

import com.library.library_management_system.enums.TransactionStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BorrowingTransactionResponseDTO {

    private Long id;
    private String bookTitle;
    private String borrowerName;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private TransactionStatus status;
}