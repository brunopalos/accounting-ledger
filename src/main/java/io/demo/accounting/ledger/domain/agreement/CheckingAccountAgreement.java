package io.demo.accounting.ledger.domain.agreement;

import io.demo.accounting.ledger.domain.postingrule.DepositPostingRule;
import io.demo.accounting.ledger.domain.postingrule.PostingRule;
import io.demo.accounting.ledger.domain.postingrule.WithdrawalPostingRule;
import io.demo.accounting.ledger.model.TransactionType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CheckingAccountAgreement {
    private final Map<TransactionType, PostingRule> postingRulesMap =  new HashMap<>();

    public CheckingAccountAgreement(
            DepositPostingRule depositPostingRule,
            WithdrawalPostingRule withdrawalPostingRule
    ) {
        postingRulesMap.put(TransactionType.DEPOSIT, depositPostingRule);
        postingRulesMap.put(TransactionType.WITHDRAWAL, withdrawalPostingRule);
    }

    public PostingRule getPostingRule(TransactionType moneyMovementType) {
        final var postingRule = postingRulesMap.get(moneyMovementType);
        if (postingRule != null) {
            return postingRule;
        } else {
            throw new IllegalStateException("Posting rule for movement type " + moneyMovementType + " is null");
        }
    }
}
