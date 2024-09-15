package com.abhaynm.library_management.service.serviceImp;

import com.abhaynm.library_management.dto.BookAddDto;
import com.abhaynm.library_management.dto.BookResponseDto;
import com.abhaynm.library_management.exceptions.coustomexceptions.BookAddingFailedException;
import com.abhaynm.library_management.exceptions.coustomexceptions.BookNotFoundException;
import com.abhaynm.library_management.model.Book;
import com.abhaynm.library_management.repository.BookRepository;
import com.abhaynm.library_management.service.BookService;
import com.abhaynm.library_management.utility.IDGenerator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookServiceImp implements BookService {
    private final BookRepository bookRepository;
    @Override
    public void addBook(BookAddDto request) {
        String generatedId;
        //generating a random id and checking if the id is already exists avoiding the conflict of id generation
        do {
            generatedId = IDGenerator.generateUniqueId();
        } while (bookRepository.existsById(generatedId));
        Book book=new Book();
        book.setId(generatedId);
        book.setBookName(request.getBookName());
        book.setAuthorName(request.getAuthorName());
        book.setDescription(request.getDescription());
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(request.getTotalCopies());
        book.setDelete(false);
        try {
            bookRepository.save(book);

        } catch (Exception e) {
           throw new BookAddingFailedException("failed to add book:"+request.getBookName());
        }
    }

    @Override
    public List<BookResponseDto> findBooks() {
        List<Book>books=bookRepository.findByIsDeleteFalse();
        log.info("available books:{}",books);
       return books.stream().map(b->{
            return new BookResponseDto(b.getId(),
                    b.getBookName(),
                    b.getAuthorName(),
                    b.getDescription(),
                    b.getTotalCopies(),
                    b.getAvailableCopies());
        }).toList();

    }

    @Override
    public void editBook(String bookId, BookAddDto request) {
        Book book = getBook(bookId);
        book.setBookName(request.getBookName());
        book.setAuthorName(request.getAuthorName());
        book.setDescription(request.getDescription());
        book.setTotalCopies(request.getTotalCopies());
        bookRepository.save(book);
    }

    @Override
    public void softDeleteBook(String bookId) {
        Book book=getBook(bookId);
        book.setDelete(true);
        bookRepository.save(book);
    }

    private Book getBook(String bookId) {
        return bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException("Book not found with this id"+ bookId));
    }
}