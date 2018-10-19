package com.vegesoft.moneytracker.account.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vegesoft.moneytracker.account.command.CreateAccountCommand;
import com.vegesoft.moneytracker.account.domain.Account;
import com.vegesoft.moneytracker.account.domain.Balance;
import com.vegesoft.moneytracker.account.domain.repository.AccountRepository;
import com.vegesoft.moneytracker.account.handler.mapper.AccountCommandMapper;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ReactiveAccountCommandHandlerTest {

    private AccountCommandHandler accountCommandHandler;
    private AccountCommandMapper accountCommandMapper;
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountCommandMapper = mock(AccountCommandMapper.class);
        accountCommandHandler = new ReactiveAccountCommandHandler(accountRepository, accountCommandMapper);
    }

    @Test
    @DisplayName("Should add account with success")
    void shouldAdd_account() {
        //Given
        final UUID id = UUID.randomUUID();
        final BigDecimal initialValue = BigDecimal.TEN;
        final CreateAccountCommand createAccountCommand = new CreateAccountCommand(initialValue);
        final Account account = new Account(id, new Balance(initialValue));

        when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(account));
        when(accountCommandMapper.mapToAccount(id, createAccountCommand)).thenReturn(account);
        //When
        final Mono<Void> add = accountCommandHandler.add(id, createAccountCommand);
        //Then
        StepVerifier.create(add).expectComplete().verify();
        verify(accountRepository).save(any(Account.class));
    }
}