package com.nicholasallum.microbank.transfer;

import java.math.BigDecimal;

public record TransferResponse(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String status) {}
