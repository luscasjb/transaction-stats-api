package com.devchallenge.transactionapi.controller;

import com.devchallenge.transactionapi.dto.StatisticsResponse;
import com.devchallenge.transactionapi.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Tag(name = "Statistics", description = "Endpoint for retrieving transaction statistics")
public class StatisticsController { 

    private final StatisticsService service;

    @GetMapping
    @Operation(
            summary = "Get transaction statistics",
            description = "Calculates and returns statistics for transactions made in the last configured time window (default 60 seconds)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Statistics calculated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = StatisticsResponse.class))
    )
    public ResponseEntity<StatisticsResponse> getStatistics() {
        return ResponseEntity.ok(service.getStatistics());
    }
}
