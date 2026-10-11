package io.demo.accounting.ledger.domain.service;

import io.demo.accounting.ledger.domain.repository.AccountRepository;
import io.demo.accounting.ledger.domain.repository.TransactionRepository;
import io.demo.accounting.ledger.model.AccountSummary;
import io.demo.accounting.ledger.model.AccountingEntry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountSummaryService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public AccountSummaryService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public BigDecimal getBalance(Long accountId) {
        return accountRepository.getBalance(accountId);
    }

    public List<AccountingEntry> getEntries(Long accountingId) {
        return transactionRepository.findEntriesByAccountId(accountingId);
    }

   synchronized public AccountSummary getSummary(Long accountId) {
       final var balance = getBalance(accountId);
       final var entries =  getEntries(accountId);
       return new AccountSummary(balance, entries);
    }
}
