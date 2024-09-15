package com.abhaynm.library_management.repository;

import com.abhaynm.library_management.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,String> {
}
