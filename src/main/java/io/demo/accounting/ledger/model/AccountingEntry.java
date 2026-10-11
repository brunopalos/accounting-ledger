package io.demo.accounting.ledger.model;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountingEntry(
        Long accountId,
        @Nullable Long transactionId,
        BigDecimal amount,
        Instant occurredAt,
        Instant effectiveAt
) {
}
