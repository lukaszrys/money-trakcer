package com.vegesoft.moneytracker.rest;

import com.vegesoft.moneytracker.command.CreateAccountCommand;
import com.vegesoft.moneytracker.domain.repository.AccountRepository;
import java.math.BigDecimal;
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
class AccountControllerIT {

    private static final String API_ACCOUNTS = "/api/accounts";

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
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
        exchange
            .expectStatus()
            .is2xxSuccessful()
            .expectBody()
            .jsonPath("$.data").isNotEmpty();
    }
}