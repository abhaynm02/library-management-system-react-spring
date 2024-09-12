package com.abhaynm.library_management.service.serviceImp;

import com.abhaynm.library_management.dto.AuthRequest;
import com.abhaynm.library_management.dto.AuthResponse;
import com.abhaynm.library_management.dto.RegisterRequest;
import com.abhaynm.library_management.model.UserEntity;
import com.abhaynm.library_management.repository.UserRepository;
import com.abhaynm.library_management.service.AuthenticationService;
import com.abhaynm.library_management.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void register(RegisterRequest request) {
        UserEntity user =new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        //saving the user in database
        userRepository.save(user);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        //finding the user and creating JWT token for validation
        UserEntity user =userRepository.findByEmail(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthResponse(user.getName(),token,user.getRole());
    }
}
