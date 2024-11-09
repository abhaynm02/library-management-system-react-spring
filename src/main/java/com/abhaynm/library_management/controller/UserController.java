package com.abhaynm.library_management.controller;

import com.abhaynm.library_management.dto.BookResponseDto;
import com.abhaynm.library_management.dto.ProfileDto;
import com.abhaynm.library_management.dto.ResponseModel;
import com.abhaynm.library_management.dto.TransactionDto;
import com.abhaynm.library_management.model.Transaction;
import com.abhaynm.library_management.service.BookService;
import com.abhaynm.library_management.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final BookService bookService;

    @GetMapping("/get/profile")
    public ResponseEntity<ResponseModel<ProfileDto>>getProfile(@RequestHeader(name="Authorization") String token){
        ResponseModel<ProfileDto> responseModel = new ResponseModel<>("success", "profile fetch successfully", userService.getProfile(token));
    return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }

    @PatchMapping("/edit/profile")
    public ResponseEntity<ResponseModel<?>>editUserProfile(@RequestHeader(name="Authorization") String token,@RequestBody ProfileDto profileDto){
       String name= profileDto.getName();
        if (name == null || name.trim().isEmpty()) {
            ResponseModel<?> responseModel = new ResponseModel<>("failure", "Name cannot be null or empty", null);
            return new ResponseEntity<>(responseModel, HttpStatus.UNPROCESSABLE_ENTITY);
        }

            userService.editUserProfile(token,name);
            ResponseModel<?>responseModel=new ResponseModel<>("success","profile updated successfully",null);
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/get/all/books")
    public ResponseEntity<ResponseModel<List<BookResponseDto>>>getAllBooks(){
        ResponseModel<List<BookResponseDto>>responseModel=new ResponseModel<>("success","book fetch successfully",bookService.findBooks());
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }
     @GetMapping("/get/borrowed/books")
    public ResponseEntity<ResponseModel<List<TransactionDto>>>findBorrowedBooks(@RequestHeader(name="Authorization") String token){
         ResponseModel<List<TransactionDto>>responseModel=new ResponseModel<>("success","fetch borrowed book  for user  successfully",userService.findTransactionForUsers(token));

    return new ResponseEntity<>(responseModel,HttpStatus.OK);
     }

     @PostMapping("/add/book/{bookId}")
    public ResponseEntity<ResponseModel<?>>borrowBook(@RequestHeader(name="Authorization") String token,@PathVariable("bookId") String bookId){
        ResponseModel<?>responseModel=new ResponseModel<>("success","add book in borrowed list",userService.borrowBook(token,bookId));
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
     }
    @PostMapping("/return/book/{bookId}")
    public ResponseEntity<ResponseModel<?>>returnBackBook(@RequestHeader(name="Authorization") String token,@PathVariable("bookId") String bookId){
        ResponseModel<?>responseModel=new ResponseModel<>("success","book return successfully",userService.returnBook(token,bookId));
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }

}
