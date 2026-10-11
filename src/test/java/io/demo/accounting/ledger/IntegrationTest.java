package io.demo.accounting.ledger;

import io.demo.accounting.ledger.infrastructure.repository.InMemoryAccountRepository;
import io.demo.accounting.ledger.infrastructure.repository.InMemoryTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class IntegrationTest {

	@Autowired
	private InMemoryAccountRepository accountRepository;

	@Autowired
	private InMemoryTransactionRepository transactionRepository;

	@BeforeEach
	void setUp() {
		accountRepository.clearAll();
		transactionRepository.clearAll();
	}
}
