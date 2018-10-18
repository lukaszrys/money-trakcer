package com.vegesoft.moneytracker.account.client;

import com.vegesoft.moneytracker.account.client.data.ExpenseRequest;
import com.vegesoft.moneytracker.account.configuration.properties.WebClientProperties;
import com.vegesoft.moneytracker.account.rest.data.ResponseWrapper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
class RestAccountHistoryClient implements AccountHistoryClient {

    private final String EXPENSE_ENDPOINT = "expenses";

    private final WebClient webClient;
    private final WebClientProperties webClientProperties;

    @Autowired
    RestAccountHistoryClient(final WebClient webClient, final WebClientProperties webClientProperties) {
        this.webClient = webClient;
        this.webClientProperties = webClientProperties;
    }

    @Override
    public Mono<UUID> expense(final ExpenseRequest expenseRequest) {
        final ParameterizedTypeReference<ResponseWrapper<UUID>> responseType = new ParameterizedTypeReference<>() {

        };

        final Mono<UUID> map = webClient.post()
            .uri(webClientProperties.getAccountHistoryUri() + EXPENSE_ENDPOINT)
            .body(Mono.just(expenseRequest), ExpenseRequest.class)
            .retrieve()
            .bodyToMono(responseType)
            .map(ResponseWrapper::getData);
        return map;
    }
}
