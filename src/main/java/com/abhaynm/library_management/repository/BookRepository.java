package com.abhaynm.library_management.repository;

import com.abhaynm.library_management.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,String> {
    List<Book> findByIsDeleteFalse();
}
