package com.nicholasallum.microbank.transaction;

import com.nicholasallum.microbank.account.BankAccount;
import com.nicholasallum.microbank.account.BankAccountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final BankAccountService accountService;
    private final BankTransactionRepository transactionRepository;

    public TransactionController(BankAccountService accountService, BankTransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/{accountNumber}")
    public List<TransactionResponse> history(@PathVariable String accountNumber) {
        BankAccount account = accountService.findActiveAccount(accountNumber);
        return transactionRepository.findByAccountOrderByCreatedAtDesc(account)
                .stream()
                .map(t -> new TransactionResponse(t.getId(), account.getAccountNumber(), t.getType(), t.getAmount(), t.getDescription(), t.getCreatedAt()))
                .toList();
    }
}
