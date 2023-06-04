package com.desafio.skytef.service;

import com.desafio.skytef.assembler.TransactionAssembler;
import com.desafio.skytef.dto.TransactionRequestDTO;
import com.desafio.skytef.dto.TransactionResponseDTO;
import com.desafio.skytef.exception.DuplicateCardNumberException;
import com.desafio.skytef.model.Transaction;
import com.desafio.skytef.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.desafio.skytef.exception.ExceptionConstants.DUPLICATED_CARD_NUMBER;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionAssembler transactionAssembler;

    public Transaction save(TransactionRequestDTO transactionRequestDTO) {
        Transaction transaction = transactionAssembler.toEntity(transactionRequestDTO);
        return transactionRepository.save(transaction);
    }

    public List<TransactionResponseDTO> findAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactionAssembler.toCollectionModel(transactions);
    }

    public TransactionResponseDTO findById(Long id) {
        Transaction transaction = transactionRepository.findById(id);
        return transactionAssembler.toModel(transaction);
    }

    public void update(Long id, TransactionRequestDTO transactionRequestDTO) {
        Transaction transaction = transactionRepository.findById(id);
        validateUniqueCardNumber(transactionRequestDTO.getCardNumber());
        transaction.setEstablishmentName(transactionRequestDTO.getEstablishmentName());
        transaction.setCardNumber(transactionRequestDTO.getCardNumber());
        transaction.setValue(transactionRequestDTO.getValue());
        transactionRepository.save(transaction);
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    private void validateUniqueCardNumber(String cardNumber) {
        try {
            if (transactionRepository.existsByCardNumber(cardNumber)) {
                throw new DuplicateCardNumberException("The card number " + cardNumber + " is already used.");
            }
        } catch (Exception e) {
            throw new DuplicateCardNumberException(DUPLICATED_CARD_NUMBER);
        }
    }
}
