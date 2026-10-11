package io.demo.accounting.ledger.domain.service;

import io.demo.accounting.ledger.domain.agreement.CheckingAccountAgreement;
import io.demo.accounting.ledger.domain.exception.InsufficientFundsException;
import io.demo.accounting.ledger.domain.postingrule.DepositPostingRule;
import io.demo.accounting.ledger.domain.postingrule.WithdrawalPostingRule;
import io.demo.accounting.ledger.domain.repository.AccountRepository;
import io.demo.accounting.ledger.domain.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CheckingAccountServiceTest {

    private final DepositPostingRule depositPostingRule = new DepositPostingRule();
    private final WithdrawalPostingRule withdrawalPostingRule = new WithdrawalPostingRule();

    @Mock
    private CheckingAccountAgreement checkingAccountAgreement;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CheckingAccountService subject;

    @Test
    void shouldDepositAccount() {
        // Setup
        final var accountId = 0L;
        final var amount = BigDecimal.TEN;
        final var transactionId = 1L;

        Mockito.when(checkingAccountAgreement.getPostingRule(Mockito.any())).thenReturn(depositPostingRule);
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transactionId);

        // Execute
        final var result = subject.deposit(accountId, amount);

        // Verify
        assertEquals(transactionId, result);
        Mockito.verify(accountRepository, Mockito.times(1)).addToBalance(0L, amount);
    }

    @Test
    void shouldWithdrawAccount() {
        // Setup
        final var accountId = 0L;
        final var amount = BigDecimal.TEN;
        final var transactionId = 1L;

        Mockito.when(checkingAccountAgreement.getPostingRule(Mockito.any())).thenReturn(withdrawalPostingRule);
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transactionId);
        Mockito.when(accountRepository.getBalance(0L)).thenReturn(BigDecimal.TEN);

        // Execute
        final var result = subject.withdrawal(accountId, amount);

        // Verify
        assertEquals(transactionId, result);
        Mockito.verify(accountRepository, Mockito.times(1)).addToBalance(0L, amount.negate());
    }

    @Test
    void shouldThrowWithdrawingAccountWithInsufficientBalance() {
        // Setup
        final var accountId = 0L;
        final var amount = BigDecimal.TEN;
        Mockito.when(accountRepository.getBalance(0L)).thenReturn(BigDecimal.ZERO);

        // Execute & Verify
        final var exception = assertThrows(
                InsufficientFundsException.class,
                () -> subject.withdrawal(accountId, amount)
        );
        assertTrue(exception.getMessage().startsWith("The account 0 has insufficient funds: 0"));
    }
}