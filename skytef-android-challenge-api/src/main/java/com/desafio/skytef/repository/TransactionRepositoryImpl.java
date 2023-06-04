package com.desafio.skytef.repository;

import com.desafio.skytef.model.Transaction;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.desafio.skytef.exception.ExceptionConstants.TRANSACTION_NOT_FOUND;

@Repository
@RequiredArgsConstructor
class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionRepositoryJpa transactionRepositoryJpa;

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepositoryJpa.save(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepositoryJpa.findAll();
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TRANSACTION_NOT_FOUND));
    }

    @Override
    public void update(Long id, Transaction transaction) {
        transactionRepositoryJpa.findById(id).orElseThrow(() -> new EntityNotFoundException(TRANSACTION_NOT_FOUND));
        transaction.setEstablishmentName(transaction.getEstablishmentName());
        transaction.setCardNumber(transaction.getCardNumber());
        transaction.setValue(transaction.getValue());
        transactionRepositoryJpa.save(transaction);
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = transactionRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TRANSACTION_NOT_FOUND));
        transactionRepositoryJpa.delete(transaction);
    }

    @Override
    public boolean existsByCardNumber(String cardNumber) {
        return transactionRepositoryJpa.existsByCardNumber(cardNumber);
    }

}
