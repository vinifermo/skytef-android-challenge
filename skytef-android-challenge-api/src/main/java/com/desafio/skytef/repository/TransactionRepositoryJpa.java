package com.desafio.skytef.repository;

import com.desafio.skytef.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepositoryJpa extends JpaRepository<Transaction, Long> {

    boolean existsByCardNumber(String cardNumber);
}
