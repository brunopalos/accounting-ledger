package io.demo.accounting.ledger.infrastructure.repository;

import io.demo.accounting.ledger.model.AccountingEntry;
import io.demo.accounting.ledger.model.Transaction;
import io.demo.accounting.ledger.model.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class InMemoryTransactionRepositoryTest {

    @InjectMocks
    private InMemoryTransactionRepository subject;

    @Test
    void shouldCreateTransaction() {
        // Setup
        final var instant = Instant.now();
        final var entry = createEntry(2L, instant);
        final var transaction = createTransaction(entry, instant);

        // Execute
        final var result = subject.save(transaction);

        // Verify
        assertEquals(1L, result);

        final var resultEntry = subject.findEntriesByAccountId(2L).getFirst();
        assertEquals(2L, resultEntry.accountId());
        assertEquals(1L, resultEntry.transactionId());
        assertEquals(BigDecimal.TEN, resultEntry.amount());
        assertEquals(instant, resultEntry.effectiveAt());
        assertEquals(instant, resultEntry.occurredAt());
    }

    @Test
    void shouldCreateMoreTransactions() {
        // Setup
        final var instant = Instant.now();
        final var entry = createEntry(3L, instant);
        final var transaction1 = createTransaction(entry, instant);
        final var transaction2 = createTransaction(entry, instant);

        // Execute
        final var result1 = subject.save(transaction1);
        final var result2 = subject.save(transaction2);

        // Verify
        assertEquals(1L, result1);
        assertEquals(2L, result2);
    }

    @Test
    void shouldClearAll() {
        // Setup
        final var instant = Instant.now();
        final var entry = createEntry(1L, instant);
        final var transaction = createTransaction(entry, instant);
        subject.save(transaction);

        // Execute
        subject.clearAll();

        // Verify
        final var result = subject.findEntriesByAccountId(1L);
        assertTrue(result.isEmpty());
    }

    private static Transaction createTransaction(AccountingEntry entry, Instant instant) {
        return new Transaction(
                null,
                TransactionType.DEPOSIT,
                List.of(entry),
                instant
        );
    }

    private static AccountingEntry createEntry(Long accountId, Instant instant) {
        return new AccountingEntry(
                accountId,
                null,
                BigDecimal.TEN,
                instant,
                instant
        );
    }
}
