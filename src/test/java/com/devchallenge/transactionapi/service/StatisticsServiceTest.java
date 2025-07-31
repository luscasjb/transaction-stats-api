package com.devchallenge.transactionapi.service;

import com.devchallenge.transactionapi.dto.StatisticsResponse;
import com.devchallenge.transactionapi.model.Transaction;
import com.devchallenge.transactionapi.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private TransactionRepository transactionRepository;

    /**
     * Tests the scenario where no transactions are registered.
     * The expected result is a statistics object with all values zeroed out.
     */
    @Test
    void getStatistics_shouldReturnZeroedStats_whenNoTransactionsExist() {
        // Arrange
        ReflectionTestUtils.setField(statisticsService, "timeWindowSeconds", 60L);
        when(transactionRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        StatisticsResponse response = statisticsService.getStatistics();

        // Assert
        assertEquals(0L, response.count());
        assertEquals(BigDecimal.ZERO, response.sum());
    }

    /**
     * Tests the scenario where transactions exist, but all of them are older
     * than the configured time window. The result should also be zeroed out.
     */
    @Test
    void getStatistics_shouldReturnZeroedStats_whenAllTransactionsAreOld() {
        // Arrange
        ReflectionTestUtils.setField(statisticsService, "timeWindowSeconds", 60L);
        List<Transaction> oldTransactions = List.of(
                new Transaction(BigDecimal.valueOf(100), OffsetDateTime.now().minusSeconds(120))
        );
        when(transactionRepository.findAll()).thenReturn(oldTransactions);

        // Act
        StatisticsResponse response = statisticsService.getStatistics();

        // Assert
        assertEquals(0L, response.count());
    }

    /**
     * Tests the happy path: a mix of recent and old transactions.
     * The calculation should only consider transactions within the time window.
     */
    @Test
    void getStatistics_shouldCalculateCorrectly_forRecentTransactions() {
        // Arrange
        ReflectionTestUtils.setField(statisticsService, "timeWindowSeconds", 60L);
        List<Transaction> recentTransactions = List.of(
                new Transaction(BigDecimal.valueOf(100.50), OffsetDateTime.now().minusSeconds(10)),
                new Transaction(BigDecimal.valueOf(50.00), OffsetDateTime.now().minusSeconds(20)),
                new Transaction(BigDecimal.valueOf(200.00), OffsetDateTime.now().minusSeconds(120)) // Old one
        );
        when(transactionRepository.findAll()).thenReturn(recentTransactions);

        // Act
        StatisticsResponse response = statisticsService.getStatistics();

        // Assert
        assertEquals(2, response.count()); // Only 2 recent transactions
        assertEquals(new BigDecimal("150.50"), response.sum());
        assertEquals(new BigDecimal("50.00"), response.min());
        assertEquals(new BigDecimal("100.50"), response.max());
        assertEquals(new BigDecimal("75.25"), response.avg());
    }
}