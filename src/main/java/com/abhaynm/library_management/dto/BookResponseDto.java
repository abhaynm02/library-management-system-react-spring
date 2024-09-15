package com.abhaynm.library_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private String bookId;
    private String bookName;
    private String authorName;
    private String description;
    private int totalCopy;
    private int availableCopy;

}
