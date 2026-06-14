package com.nicholasallum.microbank.account;

import java.math.BigDecimal;

public record AccountResponse(Long id, String accountNumber, BigDecimal balance, AccountStatus status, String ownerName) {}
