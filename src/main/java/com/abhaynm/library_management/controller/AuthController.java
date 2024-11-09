package com.abhaynm.library_management.controller;

import com.abhaynm.library_management.dto.AuthRequest;
import com.abhaynm.library_management.dto.AuthResponse;
import com.abhaynm.library_management.dto.RegisterRequest;
import com.abhaynm.library_management.dto.ResponseModel;
import com.abhaynm.library_management.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<ResponseModel<?>> register(@RequestBody @Valid RegisterRequest request) {

        if (authenticationService.isEmailExist(request.getEmail())) {
            ResponseModel<?>responseModel=new ResponseModel<>("failed","Email already exists",null);
            return new ResponseEntity<>(responseModel, HttpStatus.CONFLICT);
        }
        authenticationService.register(request);
        ResponseModel<?>responseModel=new ResponseModel<>("success","Registration successful",null);
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseModel<AuthResponse>> authenticate(@RequestBody @Valid AuthRequest request) {
        try {
            AuthResponse authResponse = authenticationService.authenticate(request);
            ResponseModel<AuthResponse> responseModel = new ResponseModel<>("success", "Token created", authResponse);
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        } catch (AuthenticationException e) {

            ResponseModel<AuthResponse> errorResponse = new ResponseModel<>("error", "Invalid credentials", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }


}
