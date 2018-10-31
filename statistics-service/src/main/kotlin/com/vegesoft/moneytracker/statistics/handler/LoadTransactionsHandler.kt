package com.vegesoft.moneytracker.statistics.handler

import com.vegesoft.moneytracker.statistics.command.LoadTransactionsCommand
import reactor.core.publisher.Mono

interface LoadTransactionsHandler {

    fun handle(command: LoadTransactionsCommand) : Mono<Void>
}