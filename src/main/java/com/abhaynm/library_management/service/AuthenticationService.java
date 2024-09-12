package com.abhaynm.library_management.service;

import com.abhaynm.library_management.dto.AuthRequest;
import com.abhaynm.library_management.dto.AuthResponse;
import com.abhaynm.library_management.dto.RegisterRequest;

public interface AuthenticationService {
    boolean isEmailExist(String email);
    void register(RegisterRequest request);
    AuthResponse authenticate(AuthRequest request);
}
