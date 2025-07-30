package com.devchallenge.transactionapi.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class Transaction {
    private BigDecimal valor;
    private OffsetDateTime dataHora;
}