package com.vegesoft.moneytracker.account.handler;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

import com.vegesoft.moneytracker.account.client.AccountHistoryClient;
import com.vegesoft.moneytracker.account.client.data.ExpenseRequest;
import com.vegesoft.moneytracker.account.client.data.IncomeRequest;
import com.vegesoft.moneytracker.account.command.AddBalanceCommand;
import com.vegesoft.moneytracker.account.command.SubtractBalanceCommand;
import com.vegesoft.moneytracker.account.domain.Account;
import com.vegesoft.moneytracker.account.domain.Balance;
import com.vegesoft.moneytracker.account.domain.repository.AccountRepository;
import com.vegesoft.moneytracker.account.handler.mapper.BalanceCommandMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ReactiveBalanceCommandHandlerTest {

    private AccountRepository accountRepository;
    private BalanceCommandHandler balanceCommandHandler;
    private AccountHistoryClient accountHistoryClient;
    private BalanceCommandMapper commandMapper;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountHistoryClient = mock(AccountHistoryClient.class);
        commandMapper = mock(BalanceCommandMapper.class);
        balanceCommandHandler = new ReactiveBalanceCommandHandler(accountRepository, accountHistoryClient,
            commandMapper);
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
        final IncomeRequest incomeRequest = new IncomeRequest(BigDecimal.TEN, UUID.randomUUID(), LocalDateTime.now());

        when(accountRepository.findById(accountId)).thenReturn(Mono.just(account));
        when(accountRepository.save(account)).thenReturn(Mono.just(account));
        when(commandMapper.incomeRequest(accountId, addBalanceCommand)).thenReturn(incomeRequest);
        when(accountHistoryClient.income(incomeRequest)).thenReturn(Mono.just(UUID.randomUUID()));
        //When
        final Mono<Void> handle = balanceCommandHandler.handle(accountId, addBalanceCommand);
        //Then
        StepVerifier.create(handle).expectComplete().verify();
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
        verify(accountHistoryClient).income(incomeRequest);
        assertNotEquals(account.getBalance(), balance);
    }

    @Test
    @DisplayName("Should decrease account balance value")
    void shouldDecrease_amount() {
        //Given
        final BigDecimal amount = BigDecimal.TEN;
        final SubtractBalanceCommand subtractBalanceCommand = new SubtractBalanceCommand(amount, "food");
        final UUID accountId = UUID.randomUUID();
        final BigDecimal balance = BigDecimal.ZERO;
        final Account account = new Account(accountId, new Balance(balance));
        final ExpenseRequest expenseRequest = new ExpenseRequest(BigDecimal.TEN, "type", UUID.randomUUID(),
            LocalDateTime.now());

        when(accountRepository.findById(accountId)).thenReturn(Mono.just(account));
        when(accountRepository.save(account)).thenReturn(Mono.just(account));
        when(commandMapper.expenseRequest(accountId, subtractBalanceCommand)).thenReturn(expenseRequest);
        when(accountHistoryClient.expense(expenseRequest)).thenReturn(Mono.just(UUID.randomUUID()));
        //When
        final Mono<Void> handle = balanceCommandHandler.handle(accountId, subtractBalanceCommand);
        //Then
        StepVerifier.create(handle).expectComplete().verify();
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
        verify(accountHistoryClient).expense(expenseRequest);
        assertNotEquals(account.getBalance(), balance);
    }
}