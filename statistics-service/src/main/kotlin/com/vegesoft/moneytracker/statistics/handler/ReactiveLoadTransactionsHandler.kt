package com.vegesoft.moneytracker.statistics.handler

import com.vegesoft.moneytracker.statistics.client.AccountHistoryClient
import com.vegesoft.moneytracker.statistics.command.LoadTransactionsCommand
import com.vegesoft.moneytracker.statistics.domain.AccountStatistic
import com.vegesoft.moneytracker.statistics.domain.AccountStatisticRange
import com.vegesoft.moneytracker.statistics.domain.repository.AccountStatisticRepository
import com.vegesoft.moneytracker.statistics.handler.mapper.LoadTransactionCommandMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.util.*

@Service
class ReactiveLoadTransactionsHandler(private val accountHistoryClient: AccountHistoryClient,
                                      private val mapper: LoadTransactionCommandMapper,
                                      private val accountStatisticRepository: AccountStatisticRepository)
    : LoadTransactionsHandler {

    override fun handle(command: LoadTransactionsCommand): Mono<Void> {
        return Flux.just(command)
                .map { loadCmd -> mapper.mapToTransactionRequest(loadCmd) }
                .flatMap { transactionRequest -> accountHistoryClient.findTransactionViews(transactionRequest) }
                .onErrorMap { error -> RuntimeException("An error occured when getting transactions", error.cause) }
                //TODO: collect info into AccountStatistic
                .flatMap { accountStatisticRepository.save(createAccountStatistic(command)) }
                .then()
    }

    private fun createAccountStatistic(command: LoadTransactionsCommand) =
            AccountStatistic(UUID.randomUUID(), AccountStatisticRange(command.from, command.to), command.accountId,
                    BigDecimal.TEN, mutableListOf(), mutableListOf())
}