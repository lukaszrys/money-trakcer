package com.vegesoft.moneytracker.statistics.client

import com.vegesoft.moneytracker.statistics.client.request.TransactionRequest
import com.vegesoft.moneytracker.statistics.client.response.TransactionResponse
import reactor.core.publisher.Flux

interface AccountHistoryClient {

    fun findTransactionViews(request: TransactionRequest): Flux<TransactionResponse>
}