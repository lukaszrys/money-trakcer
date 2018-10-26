package com.vegesoft.moneytracker.statistics.handler

import com.vegesoft.moneytracker.statistics.client.AccountHistoryClient
import com.vegesoft.moneytracker.statistics.command.LoadTransactionsCommand
import com.vegesoft.moneytracker.statistics.domain.repository.AccountStatisticRepository
import org.springframework.stereotype.Service

@Service
class ReactiveLoadTransactionsHandler(private val accountHistoryClient: AccountHistoryClient,
                                      private val accountStatisticRepeatable: AccountStatisticRepository) : LoadTransactionsHandler {

    override fun handle(command: LoadTransactionsCommand) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}