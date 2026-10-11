package io.demo.accounting.ledger.web.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record AmountDto(@Nonnull @Min(0) BigDecimal value) {}
