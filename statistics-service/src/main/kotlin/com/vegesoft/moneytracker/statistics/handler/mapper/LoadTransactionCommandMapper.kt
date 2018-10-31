package com.vegesoft.moneytracker.statistics.handler.mapper

import com.vegesoft.moneytracker.statistics.client.request.TransactionRequest
import com.vegesoft.moneytracker.statistics.client.response.TransactionResponse
import com.vegesoft.moneytracker.statistics.command.LoadTransactionsCommand
import com.vegesoft.moneytracker.statistics.domain.Transaction
import org.springframework.stereotype.Component

@Component
class LoadTransactionCommandMapper {

    fun mapToTransactionRequest(command: LoadTransactionsCommand): TransactionRequest {
        return TransactionRequest(command.accountId, command.from, command.to)
    }

    fun mapToTransaction(response: TransactionResponse): Transaction {
        return Transaction(response.amount, response.type)
    }
}