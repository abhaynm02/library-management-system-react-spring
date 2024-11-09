package com.abhaynm.library_management.controller;

import com.abhaynm.library_management.dto.*;
import com.abhaynm.library_management.model.Transaction;
import com.abhaynm.library_management.service.BookService;
import com.abhaynm.library_management.service.UserService;
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
    private final UserService userService;
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
       System.out.println(bookId);
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
   @GetMapping("/book/transactions/{bookId}")
    public ResponseEntity<ResponseModel<List<ResponseTransaction>>>findTransactionsForBook(@PathVariable("bookId")String bookId){
       ResponseModel<List<ResponseTransaction>>responseModel=new ResponseModel<>("success","fetchTransactions successfully",bookService.findTransactionForBook(bookId));

       return new ResponseEntity<>(responseModel,HttpStatus.OK);
   }
   @GetMapping("/get/all/users")
    public ResponseEntity<ResponseModel<List<ProfileDto>>>getAllUsers(){
        ResponseModel<List<ProfileDto>>responseModel=new ResponseModel<>("success","fetch All users",userService.getAllUsers());
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
   }
   @GetMapping("/get/user/transactions/{userName}")
    public ResponseEntity<ResponseModel<List<TransactionDto>>>findTransactionsOfUsers(@PathVariable("userName")String userName){
       ResponseModel<List<TransactionDto>>responseModel=new ResponseModel<>("success","fetch  user transaction",userService.findTransactionsForAdmin(userName));
   return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }
}


