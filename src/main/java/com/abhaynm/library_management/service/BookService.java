package com.abhaynm.library_management.service;

import com.abhaynm.library_management.dto.BookAddDto;
import com.abhaynm.library_management.dto.BookResponseDto;

import java.util.List;

public interface BookService {
    void addBook(BookAddDto request);
    List<BookResponseDto>findBooks();
    void editBook(String bookId,BookAddDto request);
    void softDeleteBook(String bookId);
}
