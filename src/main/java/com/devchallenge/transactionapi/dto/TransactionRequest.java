package com.devchallenge.transactionapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionRequest(
        @NotNull
        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal valor,

        @NotNull
        @PastOrPresent
        OffsetDateTime dataHora
) {}