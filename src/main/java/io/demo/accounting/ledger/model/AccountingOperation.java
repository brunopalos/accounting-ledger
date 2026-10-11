package io.demo.accounting.ledger.model;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountingOperation(
        Long accountId,
        TransactionType type,
        BigDecimal amount,
        Instant occurredAt
) {
}
