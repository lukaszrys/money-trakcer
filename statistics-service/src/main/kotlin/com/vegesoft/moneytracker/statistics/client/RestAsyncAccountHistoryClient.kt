package com.vegesoft.moneytracker.statistics.client

import com.vegesoft.moneytracker.statistics.client.request.TransactionRequest
import com.vegesoft.moneytracker.statistics.client.response.TransactionResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class RestAsyncAccountHistoryClient: AccountHistoryClient {

    override fun findTransactionViews(request: TransactionRequest): Flux<TransactionResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}