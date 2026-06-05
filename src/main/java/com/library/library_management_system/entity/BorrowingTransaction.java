package com.library.library_management_system.entity;

import com.library.library_management_system.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "borrowing_transactions")
public class BorrowingTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    private Borrower borrower;

    @Column(nullable = false)
    private LocalDate borrowDate;

    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;
}