package com.example.transactionapi.service;

import com.example.transactionapi.dto.TransactionRequest;
import com.example.transactionapi.model.Transaction;
import com.example.transactionapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    public void createTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction(request.valor(), request.dataHora());
        repository.save(transaction);
    }
}