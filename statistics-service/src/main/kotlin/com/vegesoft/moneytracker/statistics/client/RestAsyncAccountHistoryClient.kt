package com.vegesoft.moneytracker.statistics.client

import com.vegesoft.moneytracker.statistics.client.request.TransactionRequest
import com.vegesoft.moneytracker.statistics.client.response.TransactionResponse
import com.vegesoft.moneytracker.statistics.configuration.properties.WebClientProperties
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Component
class RestAsyncAccountHistoryClient(private val webClient: WebClient,
                                    private val webClientProperties: WebClientProperties)
    : AccountHistoryClient {


    override fun findTransactionViews(request: TransactionRequest): Flux<TransactionResponse> {

        return webClient.get()
                .uri(webClientProperties.accountHistoryUri + "/transactions/"
                        + request.accountId + "from=" + request.toString() + "%to=" + request.toString())
                .retrieve()
                .bodyToFlux(TransactionResponse::class.java)
    }
}