package com.abhaynm.library_management.service.serviceImp;

import com.abhaynm.library_management.repository.BookRepository;
import com.abhaynm.library_management.repository.UserRepository;
import com.abhaynm.library_management.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImpTest {
    @InjectMocks
   private UserServiceImp  userServiceImp;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  JwtService jwtService;
    @Mock
    private  BookRepository bookRepository;

     @BeforeEach
    void setUp(){
         MockitoAnnotations.openMocks(this);

     }


}