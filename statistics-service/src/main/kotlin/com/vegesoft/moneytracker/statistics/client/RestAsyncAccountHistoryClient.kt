package com.vegesoft.moneytracker.statistics.client

import com.vegesoft.moneytracker.statistics.client.request.TransactionRequest
import com.vegesoft.moneytracker.statistics.client.response.TransactionResponse
import com.vegesoft.moneytracker.statistics.configuration.properties.WebClientProperties
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Flux
import java.net.URI

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

    //FIXME -> NullPointer
    private fun uri(builder: UriBuilder, request: TransactionRequest): URI {
        return builder
                .path(webClientProperties.accountHistoryUri + "/transactions/" + request.accountId)
                .queryParam("from", request.from)
                .queryParam("to", request.to)
                .build()
    }
}