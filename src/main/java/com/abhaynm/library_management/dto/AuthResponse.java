package com.abhaynm.library_management.dto;

import com.abhaynm.library_management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AuthResponse {
    private String name;
    private String token;
    private Role role;
}
