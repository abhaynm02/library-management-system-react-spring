package com.abhaynm.library_management.dto;

import com.abhaynm.library_management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private long id;
    private String name;
    private String userName;
    private Role role;
    private int borrowedBooks;
}
