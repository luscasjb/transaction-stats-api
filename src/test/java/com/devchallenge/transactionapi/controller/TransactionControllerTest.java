package com.devchallenge.transactionapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.devchallenge.transactionapi.dto.TransactionRequest;
import com.devchallenge.transactionapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    /**
     * Tests the happy path: a request with valid data should
     * return an HTTP 201 Created status.
     */
    @Test
    void createTransaction_shouldReturnCreated_forValidRequest() throws Exception {
        TransactionRequest validRequest = new TransactionRequest(
                new BigDecimal("100.00"),
                OffsetDateTime.now().minusDays(1)
        );

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated());
    }

    /**
     * Tests a validation failure scenario: a transaction with a future date
     * should be rejected with an HTTP 422 Unprocessable Entity status.
     */
    @Test
    void createTransaction_shouldReturnUnprocessableEntity_forFutureDate() throws Exception {
        TransactionRequest invalidRequest = new TransactionRequest(
                new BigDecimal("100.00"),
                OffsetDateTime.now().plusDays(1) // Future date
        );

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isUnprocessableEntity());
    }

    /**
     * Tests another failure scenario: a transaction with a negative value
     * should be rejected with an HTTP 422 Unprocessable Entity status.
     */
    @Test
    void createTransaction_shouldReturnUnprocessableEntity_forNegativeValue() throws Exception {
        TransactionRequest invalidRequest = new TransactionRequest(
                new BigDecimal("-1.00"), // Negative value
                OffsetDateTime.now().minusDays(1)
        );

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isUnprocessableEntity());
    }
}