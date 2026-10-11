package io.demo.accounting.ledger.domain.postingrule;

import io.demo.accounting.ledger.model.AccountingEntry;
import io.demo.accounting.ledger.model.AccountingOperation;
import org.springframework.stereotype.Component;

@Component
public class WithdrawalPostingRule implements PostingRule {
    public AccountingEntry process(AccountingOperation operation) {
        final var withdrawalAmount = operation.amount().negate();
        return new AccountingEntry(operation.accountId(),
                null,
                withdrawalAmount,
                operation.occurredAt(),
                operation.occurredAt());
    }
}
