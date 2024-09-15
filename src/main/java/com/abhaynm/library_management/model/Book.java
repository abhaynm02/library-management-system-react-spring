package com.abhaynm.library_management.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Book {
    @Id
    private String id;
    private String bookName;
    private String authorName;
    private String description;
    private int availableCopies;
    private int totalCopies;
    private boolean isDelete;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();


}
