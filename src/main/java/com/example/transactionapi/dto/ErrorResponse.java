package com.example.transactionapi.dto;

import java.util.List;

public record ErrorResponse(List<ValidationError> errors) {}