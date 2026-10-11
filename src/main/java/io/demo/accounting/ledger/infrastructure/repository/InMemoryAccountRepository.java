package io.demo.accounting.ledger.infrastructure.repository;

import io.demo.accounting.ledger.domain.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryAccountRepository implements AccountRepository {
    private final Map<Long, BigDecimal> balanceByAccountId = new HashMap<>();


    @Override
    public void addToBalance(Long accountId, BigDecimal amount) {
        balanceByAccountId.putIfAbsent(accountId, BigDecimal.ZERO);
        balanceByAccountId.computeIfPresent(accountId, (_, value) -> value.add(amount));
    }

    @Override
    public BigDecimal getBalance(Long accountId) {
        return balanceByAccountId.getOrDefault(accountId, BigDecimal.ZERO);
    }

    public void clearAll() {
        balanceByAccountId.clear();
    }
}
