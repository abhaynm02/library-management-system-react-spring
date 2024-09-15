package com.abhaynm.library_management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Transaction {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate borrowedDate;
    private LocalDate returnedDate;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
