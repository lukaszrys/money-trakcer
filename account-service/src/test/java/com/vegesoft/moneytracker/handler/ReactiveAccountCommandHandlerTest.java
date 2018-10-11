package com.vegesoft.moneytracker.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vegesoft.moneytracker.command.CreateAccountCommand;
import com.vegesoft.moneytracker.domain.Account;
import com.vegesoft.moneytracker.domain.Balance;
import com.vegesoft.moneytracker.domain.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ReactiveAccountCommandHandlerTest {

    private AccountCommandHandler accountCommandHandler;
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountCommandHandler = new ReactiveAccountCommandHandler(accountRepository);
    }

    @Test
    @DisplayName("Should add account with success")
    void shouldAdd_account() {
        //Given
        final UUID id = UUID.randomUUID();
        final BigDecimal initialValue = BigDecimal.TEN;
        final String currency = "USD";
        final CreateAccountCommand createAccountCommand = new CreateAccountCommand(initialValue, currency);
        final Account account = new Account(id, new Balance(initialValue, currency));

        when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(account));
        //When
        final Mono<Void> add = accountCommandHandler.add(id, createAccountCommand);
        //Then
        StepVerifier.create(add).expectComplete().verify();
        verify(accountRepository).save(any(Account.class));
    }
}