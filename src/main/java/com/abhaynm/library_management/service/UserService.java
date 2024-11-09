package com.abhaynm.library_management.service;

import com.abhaynm.library_management.dto.ProfileDto;
import com.abhaynm.library_management.dto.TransactionDto;
import com.abhaynm.library_management.model.Transaction;

import java.util.List;

public interface UserService {
    void editUserProfile(String token,String name);
    List<TransactionDto> findTransactionForUsers(String token);
    String borrowBook(String token,String bookId);
    String returnBook(String token,String bookId);
    ProfileDto getProfile(String token);
    List<ProfileDto>getAllUsers();
    List<TransactionDto>findTransactionsForAdmin(String userName);
}
