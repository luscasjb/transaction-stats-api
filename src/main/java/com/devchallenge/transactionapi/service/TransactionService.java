package com.devchallenge.transactionapi.service;

import com.devchallenge.transactionapi.dto.TransactionRequest;
import com.devchallenge.transactionapi.model.Transaction;
import com.devchallenge.transactionapi.repository.TransactionRepository;
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