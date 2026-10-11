package io.demo.accounting.ledger.domain.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(Long accountId, BigDecimal balance) {
        super("The account " +  accountId + " has insufficient funds: " + balance);
    }
}
