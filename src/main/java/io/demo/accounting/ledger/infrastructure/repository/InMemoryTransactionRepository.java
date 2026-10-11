package io.demo.accounting.ledger.infrastructure.repository;

import io.demo.accounting.ledger.domain.repository.TransactionRepository;
import io.demo.accounting.ledger.model.AccountingEntry;
import io.demo.accounting.ledger.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<Long, List<AccountingEntry>> entriesByAccountId = new HashMap<>();
    private final List<Transaction> transactions  = new ArrayList<>();

    @Override
    public Long save(Transaction transaction) {
        var newId = 1L;
        if (!transactions.isEmpty()) {
            newId = transactions.getLast().id() + 1;
        }
        final var newTransaction = new Transaction(newId,
                transaction.type(),
                transaction.entries(),
                transaction.occurredAt());
        transaction.entries().forEach(entry -> {
            entriesByAccountId.computeIfAbsent(entry.accountId(), _ -> new ArrayList<>());
            final var newEntry = new AccountingEntry(entry.accountId(),
                    newTransaction.id(),
                    entry.amount(),
                    entry.occurredAt(), entry.effectiveAt());
            entriesByAccountId.get(entry.accountId()).add(newEntry);
        });

        transactions.add(newTransaction);
        return newId;
    }

    @Override
    public List<AccountingEntry> findEntriesByAccountId(Long accountId) {
        return entriesByAccountId.getOrDefault(accountId, new ArrayList<>());
    }

    public void clearAll() {
        entriesByAccountId.clear();
        transactions.clear();
    }
}
