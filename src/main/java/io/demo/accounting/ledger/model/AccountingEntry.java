package io.demo.accounting.ledger.model;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountingEntry(
        Long accountId,
        Long transactionId,
        BigDecimal amount,
        Instant occurredAt,
        Instant effectiveAt
) {
}
