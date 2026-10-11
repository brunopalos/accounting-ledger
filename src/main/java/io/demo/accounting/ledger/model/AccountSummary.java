package io.demo.accounting.ledger.model;

import java.math.BigDecimal;
import java.util.List;

public record AccountSummary(
        BigDecimal balance,
        List<AccountingEntry> entries
) {
}
