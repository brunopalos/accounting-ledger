package io.demo.accounting.ledger.domain.service;

import io.demo.accounting.ledger.domain.repository.AccountRepository;
import io.demo.accounting.ledger.domain.repository.TransactionRepository;
import io.demo.accounting.ledger.model.AccountingEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AccountSummaryServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountSummaryService subject;

    @Test
    void shouldGetBalance() {
        // Setup
        Mockito.when(accountRepository.getBalance(0L)).thenReturn(BigDecimal.TEN);

        // Execute
        final var result = subject.getBalance(0L);

        // Verify
        assertEquals(BigDecimal.TEN, result);
    }

    @Test
    void shouldGetEntries() {
        // Setup
        final var entry = Mockito.<AccountingEntry>mock();
        Mockito.when(transactionRepository.findEntriesByAccountId(0L)).thenReturn(List.of(entry));

        // Execute
        final var result = subject.getEntries(0L);

        // Verify
        assertEquals(entry, result.getFirst());
    }

    @Test
    void shouldGetAccountSummary() {
        // Setup
        final var entry = Mockito.<AccountingEntry>mock();
        Mockito.when(transactionRepository.findEntriesByAccountId(0L)).thenReturn(List.of(entry));
        Mockito.when(accountRepository.getBalance(0L)).thenReturn(BigDecimal.TEN);

        // Execute
        final var result = subject.getSummary(0L);

        // Verify
        assertEquals(BigDecimal.TEN, result.balance());
        assertEquals(entry, result.entries().getFirst());
    }
}
