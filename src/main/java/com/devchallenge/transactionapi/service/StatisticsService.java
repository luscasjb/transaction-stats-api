package com.devchallenge.transactionapi.service;

import com.devchallenge.transactionapi.dto.StatisticsResponse;
import com.devchallenge.transactionapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {

    private final TransactionRepository transactionRepository;

    @Value("${statistics.time-window-seconds}")
    private long timeWindowSeconds;

    public StatisticsResponse getStatistics() {
        long startTime = System.nanoTime();
        log.info("Calculating statistics for the last {} seconds.", timeWindowSeconds);
        OffsetDateTime limitDateTime = OffsetDateTime.now().minusSeconds(timeWindowSeconds);

        DoubleSummaryStatistics stats = transactionRepository.findAll().stream()
                .filter(t -> t.getDataHora().isAfter(limitDateTime))
                .collect(Collectors.summarizingDouble(t -> t.getValor().doubleValue()));

        StatisticsResponse response;
        if (stats.getCount() == 0) {
            response = new StatisticsResponse(0L, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        } else {
            response = new StatisticsResponse(
                    stats.getCount(),
                    BigDecimal.valueOf(stats.getSum()).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(stats.getAverage()).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(stats.getMin()).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(stats.getMax()).setScale(2, RoundingMode.HALF_UP)
            );
        }

        long endTime = System.nanoTime();
        long durationMillis = (endTime - startTime) / 1_000_000;
        log.info("Statistics calculation finished in {} ms. Found {} transactions.", durationMillis, stats.getCount());

        return response;
    }
}