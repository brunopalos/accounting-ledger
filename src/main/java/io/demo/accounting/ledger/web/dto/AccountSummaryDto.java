package io.demo.accounting.ledger.web.dto;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.util.List;

public record AccountSummaryDto(
        @Nonnull BigDecimal balance,
        @Nonnull List<AccountEntryDto> entries
) {}
