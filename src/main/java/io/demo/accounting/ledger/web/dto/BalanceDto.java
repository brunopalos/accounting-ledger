package io.demo.accounting.ledger.web.dto;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;

public record BalanceDto(@Nonnull BigDecimal value) {}
