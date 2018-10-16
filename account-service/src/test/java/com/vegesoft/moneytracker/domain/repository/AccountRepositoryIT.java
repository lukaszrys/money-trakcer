package com.vegesoft.moneytracker.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.vegesoft.moneytracker.AccountApplication;
import com.vegesoft.moneytracker.domain.Account;
import com.vegesoft.moneytracker.domain.AccountStatus;
import com.vegesoft.moneytracker.domain.Balance;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = AccountApplication.class)
@Tag("IT")
class AccountRepositoryIT {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Should execute create account")
    void shouldExecute_crudOperations() {
        //Given
        final UUID id = UUID.randomUUID();
        final Account account = new Account(id, new Balance(BigDecimal.TEN));
        //When
        final Mono<Account> save = accountRepository.save(account);
        //Then
        StepVerifier.create(save).assertNext(savedAccount -> {
            assertEquals(id, savedAccount.getId());
            assertEquals(AccountStatus.BASIC, savedAccount.getStatus());
        }).expectComplete().verify();
    }
}