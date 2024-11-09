package com.abhaynm.library_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private String id;
    private String bookId;
    private String bookName;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    private String status;
}
