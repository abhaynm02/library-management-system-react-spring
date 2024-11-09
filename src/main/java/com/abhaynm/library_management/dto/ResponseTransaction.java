package com.abhaynm.library_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTransaction {
    private String id;
    private String userId;
    private String userName;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    private String status;
}
