package com.example.transactionapi.dto;

import java.math.BigDecimal;

public record StatisticsResponse(
        long count,
        BigDecimal sum,
        BigDecimal avg,
        BigDecimal min,
        BigDecimal max
) {}