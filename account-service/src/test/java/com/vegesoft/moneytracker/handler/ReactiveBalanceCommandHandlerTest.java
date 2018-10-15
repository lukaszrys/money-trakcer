package com.vegesoft.moneytracker.handler;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

import com.vegesoft.moneytracker.command.AddBalanceCommand;
import com.vegesoft.moneytracker.command.SubtractBalanceCommand;
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

class ReactiveBalanceCommandHandlerTest {

    private AccountRepository accountRepository;
    private BalanceCommandHandler balanceCommandHandler;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        balanceCommandHandler = new ReactiveBalanceCommandHandler(accountRepository);
    }

    @Test
    @DisplayName("Should increase account balance value")
    void shouldIncrease_amount() {
        //Given
        final BigDecimal amount = BigDecimal.TEN;
        final AddBalanceCommand addBalanceCommand = new AddBalanceCommand(amount);
        final UUID accountId = UUID.randomUUID();
        final Balance balance = new Balance(BigDecimal.ZERO);
        final Account account = new Account(accountId, balance);

        when(accountRepository.findById(accountId)).thenReturn(Mono.just(account));
        when(accountRepository.save(account)).thenReturn(Mono.just(account));
        //When
        final Mono<Void> handle = balanceCommandHandler.addBalance(accountId, addBalanceCommand);
        //Then
        StepVerifier.create(handle).expectComplete().verify();
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
        assertNotEquals(account.getBalance(), balance);
    }

    @Test
    @DisplayName("Should decrease account balance value")
    void shouldDecrease_amount() {
        //Given
        final BigDecimal amount = BigDecimal.TEN;
        final SubtractBalanceCommand subtractBalanceCommand = new SubtractBalanceCommand(amount);
        final UUID accountId = UUID.randomUUID();
        final BigDecimal balance = BigDecimal.ZERO;
        final Account account = new Account(accountId, new Balance(balance));

        when(accountRepository.findById(accountId)).thenReturn(Mono.just(account));
        when(accountRepository.save(account)).thenReturn(Mono.just(account));
        //When
        final Mono<Void> handle = balanceCommandHandler.subtractBalance(accountId, subtractBalanceCommand);
        //Then
        StepVerifier.create(handle).expectComplete().verify();
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
        assertNotEquals(account.getBalance(), balance);
    }
}