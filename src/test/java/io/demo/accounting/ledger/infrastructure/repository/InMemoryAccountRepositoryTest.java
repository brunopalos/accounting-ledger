package io.demo.accounting.ledger.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class InMemoryAccountRepositoryTest {
    @InjectMocks
    private InMemoryAccountRepository subject;

    @Test
    void shouldReturnZeroBalance() {
        // Execute
        final var result = subject.getBalance(0L);

        // Verify
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void shouldAddToBalance() {
        // Setup
        final var accountId = 1L;

        // Execute
        subject.addToBalance(accountId, BigDecimal.TEN);
        subject.addToBalance(accountId, BigDecimal.TEN);

        // Verify
        assertEquals(new BigDecimal("20"), subject.getBalance(accountId));
    }

    @Test
    void shouldClearAll() {
        // Setup
        final var accountId = 1L;
        subject.addToBalance(accountId, BigDecimal.TEN);
        subject.addToBalance(accountId, BigDecimal.TEN);

        // Execute
        subject.clearAll();

        // Verify
        assertEquals(BigDecimal.ZERO, subject.getBalance(accountId));
    }
}