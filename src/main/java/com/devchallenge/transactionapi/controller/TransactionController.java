package com.devchallenge.transactionapi.controller;

import com.devchallenge.transactionapi.dto.TransactionRequest;
import com.devchallenge.transactionapi.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Transactions", description = "Endpoints for creating and deleting transactions")
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    @Operation(summary = "Create a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid transaction data provided"),
            @ApiResponse(responseCode = "400", description = "Invalid JSON format")
    })
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionRequest request) {
        service.createTransaction(request);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Delete all transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All transactions deleted")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteTransactions() {
        service.deleteAllTransactions();
        return ResponseEntity.ok().build();
    }
}