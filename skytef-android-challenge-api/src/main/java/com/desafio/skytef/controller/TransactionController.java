package com.desafio.skytef.controller;

import com.desafio.skytef.dto.TransactionRequestDTO;
import com.desafio.skytef.dto.TransactionResponseDTO;
import com.desafio.skytef.model.Transaction;
import com.desafio.skytef.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;


    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        Transaction save = transactionService.save(transactionRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public List<TransactionResponseDTO> findAll() {
        return transactionService.findAll();
    }

    @GetMapping("/{id}")
    public TransactionResponseDTO findById(@PathVariable Long id) {
        return transactionService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@PathVariable Long id, @Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        transactionService.update(id, transactionRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        transactionService.deleteById(id);
    }
}
