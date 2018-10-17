package com.vegesoft.moneytracker.account.rest;

import com.vegesoft.moneytracker.account.command.AddBalanceCommand;
import com.vegesoft.moneytracker.account.command.SubtractBalanceCommand;
import com.vegesoft.moneytracker.account.domain.Account;
import com.vegesoft.moneytracker.account.domain.Balance;
import com.vegesoft.moneytracker.account.domain.repository.AccountRepository;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BalanceControllerIT {

    private static final String API = "/api/accounts/{0}/balance";

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void cleanUp() {
        accountRepository.deleteAll().block();
    }

    @Test
    @DisplayName("Should add money to account")
    void shouldAddToAccount() {
        //Given
        final UUID id = UUID.randomUUID();
        accountRepository.save(new Account(id, new Balance(BigDecimal.ZERO))).block();

        final AddBalanceCommand command = new AddBalanceCommand(new BigDecimal("12.33"));
        //When
        final ResponseSpec exchange = webTestClient.post()
            .uri(MessageFormat.format(API, id) + "/add")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(command), AddBalanceCommand.class)
            .exchange();
        //Then
        exchange.expectStatus().is2xxSuccessful();
    }

    @Test
    @DisplayName("Should subtract money to account")
    void shouldSubtractToAccount() {
        //Given
        final UUID id = UUID.randomUUID();
        accountRepository.save(new Account(id, new Balance(BigDecimal.ZERO))).block();

        final SubtractBalanceCommand command = new SubtractBalanceCommand(new BigDecimal("12.33"));
        //When
        final ResponseSpec exchange = webTestClient.post()
            .uri(MessageFormat.format(API, id) + "/subtract")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(command), SubtractBalanceCommand.class)
            .exchange();
        //Then
        exchange.expectStatus().is2xxSuccessful();
    }
}