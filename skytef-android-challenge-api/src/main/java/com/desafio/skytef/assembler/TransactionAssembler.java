package com.desafio.skytef.assembler;

import com.desafio.skytef.dto.TransactionRequestDTO;
import com.desafio.skytef.dto.TransactionResponseDTO;
import com.desafio.skytef.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionAssembler {

    public Transaction toEntity(TransactionRequestDTO transactionRequestDTO) {
        Transaction transaction = new Transaction();

        transaction.setId(transaction.getId());
        transaction.setEstablishmentName(transactionRequestDTO.getEstablishmentName());
        transaction.setCardNumber(transactionRequestDTO.getCardNumber());
        transaction.setValue(transactionRequestDTO.getValue());

        return transaction;
    }

    public TransactionResponseDTO toModel(Transaction transaction) {
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();

        transactionResponseDTO.setId(transaction.getId());
        transactionResponseDTO.setEstablishmentName(transaction.getEstablishmentName());
        transactionResponseDTO.setCardNumber(transaction.getCardNumber());
        transactionResponseDTO.setValue(transaction.getValue());

        return transactionResponseDTO;
    }

    public List<TransactionResponseDTO> toCollectionModel(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
