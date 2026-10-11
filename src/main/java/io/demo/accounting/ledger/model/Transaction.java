package io.demo.accounting.ledger.model;

import java.time.Instant;
import java.util.List;

public record Transaction(
        Long id,
        TransactionType type,
        List<AccountingEntry> entries,
        Instant occurredAt
) {
}
