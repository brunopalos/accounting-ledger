package io.demo.accounting.ledger.domain.repository;

import java.math.BigDecimal;

public interface AccountRepository {
    void addToBalance(Long accountId, BigDecimal amount);

    BigDecimal getBalance(Long accountId);
}
