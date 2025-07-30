package com.devchallenge.transactionapi.controller;

import com.devchallenge.transactionapi.dto.TransactionRequest;
import com.devchallenge.transactionapi.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionRequest request) {
        service.createTransaction(request);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTransactions() {
        service.deleteAllTransactions();
        return ResponseEntity.ok().build();
    }
}