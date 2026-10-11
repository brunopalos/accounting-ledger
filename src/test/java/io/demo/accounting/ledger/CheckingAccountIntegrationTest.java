package io.demo.accounting.ledger;

import io.demo.accounting.ledger.domain.service.AccountSummaryService;
import io.demo.accounting.ledger.domain.service.CheckingAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan(basePackages = {
        "io.demo.accounting.ledger.domain",
        "io.demo.accounting.ledger.infrastructure"
})
public class CheckingAccountIntegrationTest extends IntegrationTest {

    @Autowired
    private CheckingAccountService checkingAccountService;

    @Autowired
    private AccountSummaryService accountSummaryService;

	@Test
	void shouldDepositAmount() {
        // Setup
        final var accountId = 0L;

        // Execute
        checkingAccountService.deposit(accountId, BigDecimal.TEN);

        // Verify
        final var balance = accountSummaryService.getBalance(accountId);
        assertEquals(BigDecimal.TEN, balance);
	}

    @Test
    void shouldWithdrawalAmount() {
        // Setup
        final var accountId = 0L;
        checkingAccountService.deposit(accountId, BigDecimal.TEN);

        // Execute
        checkingAccountService.withdrawal(accountId, BigDecimal.TWO);

        // Verify
        final var balance = accountSummaryService.getBalance(accountId);
        assertEquals(new BigDecimal("8"), balance);
    }

    @Test
    void shouldSummarizeAccount() {
        // Setup
        final var accountId = 0L;
        checkingAccountService.deposit(accountId, BigDecimal.TEN);
        checkingAccountService.withdrawal(accountId, BigDecimal.TWO);

        // Execute
        final var accountSummary = accountSummaryService.getSummary(accountId);

        // Verify
        assertEquals(new BigDecimal("8"), accountSummary.balance());

        final var firstEntry = accountSummary.entries().getFirst();
        assertEquals(accountId, firstEntry.accountId());
        assertEquals(1L, firstEntry.transactionId());
        assertEquals(BigDecimal.TEN, firstEntry.amount());

        final var lastEntry = accountSummary.entries().getLast();
        assertEquals(2L, lastEntry.transactionId());
        assertEquals(accountId, lastEntry.accountId());
        assertEquals(BigDecimal.TWO.negate(), lastEntry.amount());
    }
}
