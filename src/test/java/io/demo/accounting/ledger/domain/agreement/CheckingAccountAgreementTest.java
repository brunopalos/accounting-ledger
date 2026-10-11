package io.demo.accounting.ledger.domain.agreement;

import io.demo.accounting.ledger.domain.postingrule.DepositPostingRule;
import io.demo.accounting.ledger.domain.postingrule.WithdrawalPostingRule;
import io.demo.accounting.ledger.model.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CheckingAccountAgreementTest {

    @Mock
    private DepositPostingRule depositPostingRule;

    @Mock
    private WithdrawalPostingRule withdrawalPostingRule;

    @InjectMocks
    private CheckingAccountAgreement subject;

    @Test
    public void shouldGetDepositPostingRule() {
        final var result = subject.getPostingRule(TransactionType.DEPOSIT);
        assertEquals(depositPostingRule, result);
    }

    @Test
    public void shouldGetWithdrawalPostingRule() {
        final var result = subject.getPostingRule(TransactionType.WITHDRAWAL);
        assertEquals(withdrawalPostingRule, result);
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfTransactionTypeIsUnknown() {
        final var exception = assertThrows(
                IllegalStateException.class,
                () -> subject.getPostingRule(Mockito.mock(TransactionType.class))
        );
        assertTrue(exception.getMessage().startsWith("Posting rule for movement type"));
    }
}