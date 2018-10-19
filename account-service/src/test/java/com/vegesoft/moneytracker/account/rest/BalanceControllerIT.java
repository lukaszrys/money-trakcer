package com.vegesoft.moneytracker.account.rest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.vegesoft.moneytracker.account.command.AddBalanceCommand;
import com.vegesoft.moneytracker.account.command.SubtractBalanceCommand;
import com.vegesoft.moneytracker.account.configuration.properties.WebClientProperties;
import com.vegesoft.moneytracker.account.domain.Account;
import com.vegesoft.moneytracker.account.domain.Balance;
import com.vegesoft.moneytracker.account.domain.repository.AccountRepository;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.function.Consumer;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BalanceControllerIT {

    private static final String API = "/api/accounts/{0}/balance";

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private WebClientProperties webClientProperties;

    private MockWebServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        final String accountHistoryUri = webClientProperties.getAccountHistoryUri();
        server.start(Integer.valueOf(accountHistoryUri.substring(accountHistoryUri.indexOf("t:") + 2)));
    }

    @AfterEach
    void cleanUp() throws IOException {
        accountRepository.deleteAll().block();
        server.shutdown();
    }

    @Test
    @DisplayName("Should add money to account")
    void shouldAddToAccount() throws JSONException {
        //Given
        prepareResponseFromAccountHistory();

        final UUID id = UUID.randomUUID();
        final AddBalanceCommand command = new AddBalanceCommand(new BigDecimal("12.33"));
        final BigDecimal initialBalance = BigDecimal.ZERO;

        accountRepository.save(new Account(id, new Balance(BigDecimal.ZERO))).block();
        //When
        final ResponseSpec exchange = webTestClient.post()
            .uri(MessageFormat.format(API, id) + "/add")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(command), AddBalanceCommand.class)
            .exchange();
        //Then
        exchange.expectStatus().is2xxSuccessful();

        StepVerifier.create(accountRepository.findById(id)).assertNext((account -> {
            assertNotEquals(account.getBalance().getAmount(), initialBalance);
        })).expectComplete().verify();
    }

    @Test
    @DisplayName("Should subtract money to account")
    void shouldSubtractToAccount() throws JSONException {
        //Given
        prepareResponseFromAccountHistory();

        final UUID id = UUID.randomUUID();
        final SubtractBalanceCommand command = new SubtractBalanceCommand(new BigDecimal("12.33"), "food");
        final BigDecimal initialBalance = BigDecimal.ZERO;

        accountRepository.save(new Account(id, new Balance(initialBalance))).block();
        //When
        final ResponseSpec exchange = webTestClient.post()
            .uri(MessageFormat.format(API, id) + "/subtract")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(command), SubtractBalanceCommand.class)
            .exchange();
        //Then
        exchange.expectStatus().is2xxSuccessful();

        StepVerifier.create(accountRepository.findById(id)).assertNext((account -> {
            assertNotEquals(account.getBalance().getAmount(), initialBalance);
        })).expectComplete().verify();
    }

    private void prepareResponseFromAccountHistory() throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("data", "b54691da-37cf-4695-b6a2-26b17afb3cad");
        prepareResponse(response -> response.setHeader("Content-Type", "application/json").setBody(json.toString()));
    }

    private void prepareResponse(Consumer<MockResponse> consumer) {
        MockResponse response = new MockResponse();
        consumer.accept(response);
        this.server.enqueue(response);
    }
}