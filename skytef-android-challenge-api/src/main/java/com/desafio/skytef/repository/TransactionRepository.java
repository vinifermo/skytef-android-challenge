package com.desafio.skytef.repository;

import com.desafio.skytef.model.Transaction;

import java.util.List;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    List<Transaction> findAll();

    Transaction findById(Long id);

    void update(Long id, Transaction transaction);

    void deleteById(Long id);

    boolean existsByCardNumber(String cardNumber);
}
