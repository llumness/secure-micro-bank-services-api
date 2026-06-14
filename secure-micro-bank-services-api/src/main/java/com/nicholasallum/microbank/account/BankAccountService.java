package com.nicholasallum.microbank.account;

import com.nicholasallum.microbank.common.ApiException;
import com.nicholasallum.microbank.transaction.BankTransaction;
import com.nicholasallum.microbank.transaction.BankTransactionRepository;
import com.nicholasallum.microbank.transaction.TransactionType;
import com.nicholasallum.microbank.user.BankUser;
import com.nicholasallum.microbank.user.BankUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class BankAccountService {
    private final BankAccountRepository accountRepository;
    private final BankUserRepository userRepository;
    private final BankTransactionRepository transactionRepository;

    public BankAccountService(BankAccountRepository accountRepository, BankUserRepository userRepository, BankTransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        BankUser user = userRepository.findById(request.userId()).orElseThrow(() -> new ApiException("User not found"));
        BankAccount account = new BankAccount();
        account.setOwner(user);
        account.setAccountNumber("MB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        account.setBalance(request.openingBalance() == null ? BigDecimal.ZERO : request.openingBalance());
        BankAccount saved = accountRepository.save(account);

        if (saved.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            record(saved, TransactionType.DEPOSIT, saved.getBalance(), "Opening balance");
        }
        return toResponse(saved);
    }

    public List<AccountResponse> findAll() {
        return accountRepository.findAll().stream().map(this::toResponse).toList();
    }

    public BankAccount findActiveAccount(String accountNumber) {
        BankAccount account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ApiException("Account not found"));
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new ApiException("Account is not active");
        }
        return account;
    }

    void record(BankAccount account, TransactionType type, BigDecimal amount, String description) {
        BankTransaction transaction = new BankTransaction();
        transaction.setAccount(account);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transactionRepository.save(transaction);
    }

    AccountResponse toResponse(BankAccount account) {
        return new AccountResponse(account.getId(), account.getAccountNumber(), account.getBalance(), account.getStatus(), account.getOwner().getFullName());
    }
}
