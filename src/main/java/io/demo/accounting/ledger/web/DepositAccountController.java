package io.demo.accounting.ledger.web;

import io.demo.accounting.ledger.domain.exception.InsufficientFundsException;
import io.demo.accounting.ledger.domain.service.AccountSummaryService;
import io.demo.accounting.ledger.domain.service.CheckingAccountService;
import io.demo.accounting.ledger.web.dto.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deposit-account")
public class DepositAccountController {
    private final CheckingAccountService checkingAccountService;
    private final AccountSummaryService accountSummaryService;

    public DepositAccountController(
            CheckingAccountService checkingAccountService,
            AccountSummaryService accountSummaryService
    ) {
        this.checkingAccountService = checkingAccountService;
        this.accountSummaryService = accountSummaryService;
    }

    @PostMapping("/deposit/{accountId}")
    public ResponseDto deposit(@PathVariable Long accountId, @RequestBody @Validated AmountDto amount) {
        final var transactionId = checkingAccountService.deposit(accountId, amount.value());
        return ResponseDto.success(transactionId);
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseDto withdraw(@PathVariable Long accountId, @RequestBody @Validated AmountDto amount) {
        try {
            final var transactionId = checkingAccountService.withdrawal(accountId, amount.value());
            return new ResponseDto(transactionId, null);
        } catch (InsufficientFundsException ex) {
            return ResponseDto.error(ex.getMessage());
        }
    }

    @GetMapping("/balance/{accountId}")
    public BalanceDto balance(@PathVariable Long accountId) {
        return new BalanceDto(accountSummaryService.getBalance(accountId));
    }

    @GetMapping("/summary/{accountId}")
    public AccountSummaryDto summary(@PathVariable Long accountId) {
        final var accountSummary =  accountSummaryService.getSummary(accountId);
        final var dtoEntries = accountSummary.entries().stream()
                .map(e -> new AccountEntryDto(e.transactionId(), e.amount(), e.occurredAt()))
                .toList();
        return new AccountSummaryDto(accountSummary.balance(), dtoEntries);
    }
}
