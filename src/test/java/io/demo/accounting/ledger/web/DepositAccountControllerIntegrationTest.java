package io.demo.accounting.ledger.web;

import io.demo.accounting.ledger.domain.exception.InsufficientFundsException;
import io.demo.accounting.ledger.domain.service.AccountSummaryService;
import io.demo.accounting.ledger.domain.service.CheckingAccountService;
import io.demo.accounting.ledger.model.AccountSummary;
import io.demo.accounting.ledger.model.AccountingEntry;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepositAccountController.class)
class DepositAccountControllerIntegrationTest {

    @MockitoBean
    private CheckingAccountService checkingAccountService;

    @MockitoBean
    private AccountSummaryService accountSummaryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldDeposit() throws Exception {
        // Setup
        final var accountId = 1L;
        final var transactionId = 3L;
        Mockito.when(checkingAccountService.deposit(accountId, BigDecimal.TEN)).thenReturn(transactionId);

        // Execute & Verify
        mockMvc.perform(post("/api/v1/deposit-account/deposit/{accountId}", accountId)
                        .contentType(MediaType.APPLICATION_JSON).content("{\"value\":\"10\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(transactionId));
    }

    @Test
    void shouldWithdraw() throws Exception {
        // Setup
        final var accountId = 1L;
        final var transactionId = 3L;
        Mockito.when(checkingAccountService.withdrawal(accountId, BigDecimal.TEN)).thenReturn(transactionId);

        // Execute & Verify
        mockMvc.perform(post("/api/v1/deposit-account/withdraw/{accountId}", accountId)
                        .contentType(MediaType.APPLICATION_JSON).content("{\"value\":\"10\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(transactionId));
    }

    @Test
    void shouldReturnInsufficientFundsWhenWithdrawing() throws Exception {
        // Setup
        final var accountId = 1L;
        Mockito.when(checkingAccountService.withdrawal(accountId, BigDecimal.TEN))
                .thenThrow(new InsufficientFundsException(accountId,  BigDecimal.ONE));

        // Execute & Verify
        mockMvc.perform(post("/api/v1/deposit-account/withdraw/{accountId}", accountId)
                        .contentType(MediaType.APPLICATION_JSON).content("{\"value\":\"10\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorMessage")
                        .value("The account 1 has insufficient funds: 1"));
    }

    @Test
    void shouldReturnBalance() throws Exception {
        // Setup
        final var accountId = 1L;
        Mockito.when(accountSummaryService.getBalance(accountId)).thenReturn(BigDecimal.TEN);

        // Execute & Verify
        mockMvc.perform(get("/api/v1/deposit-account/balance/{accountId}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(10));
    }

    @Test
    void shouldReturnSummary() throws Exception {
        // Setup
        final var accountId = 1L;
        final  var transactionId = 3L;
        final var instant =  Instant.now();
        final var entry = new AccountingEntry(accountId, transactionId, BigDecimal.TEN, instant, instant);
        Mockito.when(accountSummaryService.getSummary(accountId))
                .thenReturn(new AccountSummary(BigDecimal.TWO, List.of(entry)));

        // Execute & Verify
        mockMvc.perform(get("/api/v1/deposit-account/summary/{accountId}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(2))
                .andExpect(jsonPath("$.entries[0].transactionId").value(transactionId))
                .andExpect(jsonPath("$.entries[0].amount").value(10))
                .andExpect(jsonPath("$.entries[0].occurredAt").value(instant.toString()));
    }
}