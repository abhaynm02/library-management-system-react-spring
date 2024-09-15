package com.abhaynm.library_management.controller;

import com.abhaynm.library_management.dto.BookAddDto;
import com.abhaynm.library_management.dto.BookResponseDto;
import com.abhaynm.library_management.dto.ResponseModel;
import com.abhaynm.library_management.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final BookService bookService;
    @GetMapping("/test")
    public ResponseEntity<?>testMethod(){
        return new ResponseEntity<>("admin role tested", HttpStatus.OK);
    }
    @PostMapping("/add/book")
    public ResponseEntity<Void>createBook(@RequestBody BookAddDto request){
        bookService.addBook(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/books")
    public ResponseEntity<ResponseModel<List<BookResponseDto>>> getAllBooks() {
        List<BookResponseDto> books = bookService.findBooks();
        ResponseModel<List<BookResponseDto>> responseModel = new ResponseModel<>(
                "success",
                "fetch available books",
                books
        );
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
   @PutMapping("/book/edit/{bookId}")
    public ResponseEntity<ResponseModel<?>>editBook(@PathVariable("bookId")String bookId,@RequestBody BookAddDto request){
        bookService.editBook(bookId,request);
        ResponseModel<?>responseModel=new ResponseModel<>("success","edit book successfully",null);
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
   }
   @DeleteMapping("/book/delete/{bookId}")
    public ResponseEntity<ResponseModel<?>>softDeleteBook(@PathVariable("bookId")String bookId){
        bookService.softDeleteBook(bookId);
        ResponseModel<?>responseModel=new ResponseModel<>("success","book is successfully deleted",null);
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
   }
}


