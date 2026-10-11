package io.demo.accounting.ledger.web.dto;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountEntryDto(
        @Nonnull Long transactionId,
        @Nonnull BigDecimal amount,
        @Nonnull Instant occurredAt
) {}