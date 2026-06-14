package com.nicholasallum.microbank.transfer;

import com.nicholasallum.microbank.account.BankAccount;
import com.nicholasallum.microbank.account.BankAccountRepository;
import com.nicholasallum.microbank.account.BankAccountService;
import com.nicholasallum.microbank.common.ApiException;
import com.nicholasallum.microbank.transaction.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    private final BankAccountService accountService;
    private final BankAccountRepository accountRepository;

    public TransferService(BankAccountService accountService, BankAccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        if (request.fromAccountNumber().equals(request.toAccountNumber())) {
            throw new ApiException("Cannot transfer to the same account");
        }
        BankAccount from = accountService.findActiveAccount(request.fromAccountNumber());
        BankAccount to = accountService.findActiveAccount(request.toAccountNumber());
        if (from.getBalance().compareTo(request.amount()) < 0) {
            throw new ApiException("Insufficient funds");
        }
        from.setBalance(from.getBalance().subtract(request.amount()));
        to.setBalance(to.getBalance().add(request.amount()));
        accountRepository.save(from);
        accountRepository.save(to);
        accountService.record(from, TransactionType.TRANSFER_OUT, request.amount(), request.description());
        accountService.record(to, TransactionType.TRANSFER_IN, request.amount(), request.description());
        return new TransferResponse(from.getAccountNumber(), to.getAccountNumber(), request.amount(), "COMPLETED");
    }
}
