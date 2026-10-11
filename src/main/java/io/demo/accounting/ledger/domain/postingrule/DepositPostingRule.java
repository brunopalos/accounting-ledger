package io.demo.accounting.ledger.domain.postingrule;

import io.demo.accounting.ledger.model.AccountingEntry;
import io.demo.accounting.ledger.model.AccountingOperation;
import org.springframework.stereotype.Component;

@Component
public class DepositPostingRule implements PostingRule {
    public AccountingEntry process(AccountingOperation operation) {
        return new AccountingEntry(operation.accountId(),
                null,
                operation.amount(),
                operation.occurredAt(),
                operation.occurredAt());
    }
}
