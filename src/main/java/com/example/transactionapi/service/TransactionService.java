package com.example.transactionapi.service;

import com.example.transactionapi.dto.TransactionRequest;
import com.example.transactionapi.model.Transaction;
import com.example.transactionapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository repository;

    public void createTransaction(TransactionRequest request) {
        log.info("Receiving new transaction request.");
        Transaction transaction = new Transaction(request.valor(), request.dataHora());
        repository.save(transaction);
        log.info("Transaction saved successfully.");
    }

    public void deleteAllTransactions() {
        log.warn("Deleting all transactions.");
        repository.deleteAll();
        log.info("All transactions deleted.");
    }
}