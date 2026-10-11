package io.demo.accounting.ledger.domain.service;

import io.demo.accounting.ledger.domain.agreement.CheckingAccountAgreement;
import io.demo.accounting.ledger.domain.exception.InsufficientFundsException;
import io.demo.accounting.ledger.domain.repository.AccountRepository;
import io.demo.accounting.ledger.domain.repository.TransactionRepository;
import io.demo.accounting.ledger.model.AccountingOperation;
import io.demo.accounting.ledger.model.Transaction;
import io.demo.accounting.ledger.model.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class CheckingAccountService {
    private final CheckingAccountAgreement checkingAccountAgreement;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public CheckingAccountService(
            CheckingAccountAgreement checkingAccountAgreement,
            TransactionRepository transactionRepository,
            AccountRepository accountRepository
    ) {
        this.checkingAccountAgreement = checkingAccountAgreement;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Deposits money on the checking account.
     * <p>
     * This operation needs to be atomic because the transaction is created along with the account balance update, which
     * explains the synchronized.
     *
     * @param accountId the account ID to deposit the money
     * @param amount the amount to deposit
     * @return the transaction ID of the created deposit transaction
     */
    synchronized public Long deposit(Long accountId, BigDecimal amount) {
        final var effectiveAt =  Instant.now();
        final var operation = new AccountingOperation(accountId, TransactionType.DEPOSIT, amount, effectiveAt);
        return createTransaction(operation);
    }

    /**
     * Withdraws money from the checking account.
     * <p>
     * This operation needs to be atomic because the account balance is checked to verify if the operation can proceed,
     * and transaction is created along with the account balance update, which explains the synchronized.
     *
     * @param accountId the account ID to deposit the money
     * @param amount the amount to deposit
     * @return the transaction ID of the created deposit transaction
     */
    synchronized public Long withdrawal(Long accountId, BigDecimal amount) {
        final var effectiveAt =  Instant.now();
        final var currentBalance = accountRepository.getBalance(accountId);

        if (currentBalance.compareTo(amount) >= 0) {
            final var operation = new AccountingOperation(accountId, TransactionType.WITHDRAWAL, amount, effectiveAt);
            return createTransaction(operation);
        } else {
            throw new InsufficientFundsException(accountId, currentBalance);
        }
    }

    private Long createTransaction(AccountingOperation operation) {
        final var postingRule = checkingAccountAgreement.getPostingRule(operation.type());
        final var entry = postingRule.process(operation);
        final var transaction = new Transaction(null, operation.type(), List.of(entry), operation.occurredAt());
        final var transactionId = transactionRepository.save(transaction);

        accountRepository.addToBalance(operation.accountId(), entry.amount());
        return transactionId;
    }
}
