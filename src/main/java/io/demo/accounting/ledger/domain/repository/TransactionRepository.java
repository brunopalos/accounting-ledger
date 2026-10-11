package io.demo.accounting.ledger.domain.repository;

import io.demo.accounting.ledger.model.AccountingEntry;
import io.demo.accounting.ledger.model.Transaction;

import java.util.List;

public interface TransactionRepository {
    Long save(Transaction transaction);

    List<AccountingEntry> findEntriesByAccountId(Long accountId);
}
