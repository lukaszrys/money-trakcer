package com.vegesoft.moneytracker.account.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.vegesoft.moneytracker.account.domain.Account;
import com.vegesoft.moneytracker.account.domain.Balance;
import com.vegesoft.moneytracker.account.domain.repository.AccountRepository;
import com.vegesoft.moneytracker.account.query.view.AccountView;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class MongoRepositoryAccountQueryTest {

    private AccountRepository accountRepository;
    private AccountQuery accountQuery;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountQuery = new MongoRepositoryAccountQuery(accountRepository);
    }

    @Test
    @DisplayName("Should find by id")
    void shouldFind_byId() {
        //Given
        final UUID id = UUID.randomUUID();
        final BigDecimal amount = new BigDecimal("12.3");
        final Account account = new Account(id, new Balance(amount));

        when(accountRepository.findById(id)).thenReturn(Mono.just(account));
        //When
        final FirstStep<AccountView> accountViewFirstStep = StepVerifier.create(accountQuery.findById(id));
        //Then
        accountViewFirstStep.assertNext((accountView -> {
            assertNotNull(accountView);
            assertEquals(id, accountView.getAccountId());
            assertEquals(amount, accountView.getAmount());
        })).expectComplete().verify();
    }

    @Test
    @DisplayName("Should not find by id")
    void shouldNotFind_byId() {
        //Given
        final UUID id = UUID.randomUUID();

        when(accountRepository.findById(id)).thenReturn(Mono.empty());
        //When
        final FirstStep<AccountView> accountViewFirstStep = StepVerifier.create(accountQuery.findById(id));
        //Then
        accountViewFirstStep.expectNextCount(0).expectComplete().verify();
    }
}