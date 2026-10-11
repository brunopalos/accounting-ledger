package io.demo.accounting.ledger.web.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountEntryDto(
        Long transactionId,
        BigDecimal amount,
        Instant occurredAt
) {}