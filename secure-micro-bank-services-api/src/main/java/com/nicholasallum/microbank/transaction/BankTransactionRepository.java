package com.nicholasallum.microbank.transaction;

import com.nicholasallum.microbank.account.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    List<BankTransaction> findByAccountOrderByCreatedAtDesc(BankAccount account);
}
