package io.demo.accounting.ledger.model;

import jakarta.annotation.Nullable;

import java.time.Instant;
import java.util.List;

public record Transaction(
        @Nullable Long id,
        TransactionType type,
        List<AccountingEntry> entries,
        Instant occurredAt
) {
}
