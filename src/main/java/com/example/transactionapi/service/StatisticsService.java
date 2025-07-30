package com.example.transactionapi.service;

import com.example.transactionapi.dto.StatisticsResponse;
import com.example.transactionapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final TransactionRepository transactionRepository;

    public StatisticsResponse getStatistics() {
        OffsetDateTime sixtySecondsAgo = OffsetDateTime.now().minusSeconds(60);

        DoubleSummaryStatistics stats = transactionRepository.findAll().stream()
                .filter(t -> t.getDataHora().isAfter(sixtySecondsAgo))
                .collect(Collectors.summarizingDouble(t -> t.getValor().doubleValue()));

        if (stats.getCount() == 0) {
            return new StatisticsResponse(0L, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        return new StatisticsResponse(
                stats.getCount(),
                BigDecimal.valueOf(stats.getSum()).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(stats.getAverage()).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(stats.getMin()).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(stats.getMax()).setScale(2, RoundingMode.HALF_UP)
        );
    }
}