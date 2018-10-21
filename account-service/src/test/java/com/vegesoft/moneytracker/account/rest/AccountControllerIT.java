package com.vegesoft.moneytracker.account.rest;

import com.vegesoft.moneytracker.account.command.CreateAccountCommand;
import com.vegesoft.moneytracker.account.domain.Account;
import com.vegesoft.moneytracker.account.domain.Balance;
import com.vegesoft.moneytracker.account.domain.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AccountControllerIT {

    private static final String API_ACCOUNTS = "/api/accounts";

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    @BeforeEach
    void cleanUp() {
        accountRepository.deleteAll().block();
    }

    @Test
    @DisplayName("Should save account to repository")
    void shouldSaveAccount_toRepository() {
        //Given
        final CreateAccountCommand command = new CreateAccountCommand(BigDecimal.TEN);
        //When
        final ResponseSpec exchange = webTestClient.post()
            .uri(API_ACCOUNTS)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(command), CreateAccountCommand.class)
            .exchange();
        //Then
        exchange.expectStatus().is2xxSuccessful().expectBody().jsonPath("$.data").isNotEmpty();

        StepVerifier.create(accountRepository.findAll())
            .assertNext((Assertions::assertNotNull))
            .expectComplete()
            .verify();

    }

    @Test
    @DisplayName("Should get account view based on id")
    void shouldGet_byAccountId() {
        //Given
        final UUID accountId = UUID.randomUUID();
        final BigDecimal amount = BigDecimal.TEN;

        accountRepository.save(new Account(accountId, new Balance(amount))).block();
        //When
        final ResponseSpec exchange = webTestClient.get()
            .uri(API_ACCOUNTS + "/" + accountId)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange();
        //Then

        exchange.expectStatus()
            .is2xxSuccessful()
            .expectBody()
            .jsonPath("$.accountId", accountId)
            .hasJsonPath()
            .jsonPath("$.amount", amount);
    }

    @Test
    @DisplayName("Should not get account view based on id")
    void shouldNotGet_byAccountId() {
        //Given
        final UUID accountId = UUID.randomUUID();

        //When
        final ResponseSpec exchange = webTestClient.get()
            .uri(API_ACCOUNTS + "/" + accountId)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange();
        //Then

        exchange.expectStatus().is4xxClientError();
    }
}