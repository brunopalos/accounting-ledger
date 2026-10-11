package io.demo.accounting.ledger.domain.postingrule;

import io.demo.accounting.ledger.model.AccountingEntry;
import io.demo.accounting.ledger.model.AccountingOperation;

public interface PostingRule {
    AccountingEntry process(AccountingOperation operation);
}
