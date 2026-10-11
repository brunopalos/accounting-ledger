package io.demo.accounting.ledger.domain.postingrule;

import io.demo.accounting.ledger.model.AccountingOperation;
import io.demo.accounting.ledger.model.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WithdrawalPostingRuleTest {
    @InjectMocks
    private WithdrawalPostingRule subject;

    @Test
    void shouldCreateNewAccountingEntry() {
        // Setup
        final var operation = new AccountingOperation(
                0L,
                TransactionType.WITHDRAWAL,
                BigDecimal.TEN,
                Instant.now()
        );

        // Execute
        final var entry = subject.process(operation);

        // Verify
        assertEquals(operation.accountId(), entry.accountId());
        assertEquals(operation.amount().negate(), entry.amount());
        assertEquals(operation.occurredAt(), entry.occurredAt());
        assertEquals(operation.occurredAt(), entry.effectiveAt());
    }

}