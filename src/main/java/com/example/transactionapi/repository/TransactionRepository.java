package com.example.transactionapi.repository;

import com.example.transactionapi.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class TransactionRepository {

    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();

    public void save(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public List<Transaction> findAll() {
        return this.transactions;
    }

    public void deleteAll() {
        this.transactions.clear();
    }
}