package com.nicholasallum.microbank.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(Long id, String accountNumber, TransactionType type, BigDecimal amount, String description, LocalDateTime createdAt) {}
