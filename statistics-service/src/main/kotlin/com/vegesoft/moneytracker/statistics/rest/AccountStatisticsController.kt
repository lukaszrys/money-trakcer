package com.vegesoft.moneytracker.statistics.rest

import com.vegesoft.moneytracker.statistics.command.LoadTransactionsCommand
import com.vegesoft.moneytracker.statistics.handler.LoadTransactionsHandler
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@Validated
class AccountStatisticsController constructor(private val handler: LoadTransactionsHandler) {

    @PostMapping("/api/statistics")
    fun createStatistics(@RequestBody command: LoadTransactionsCommand): Mono<Void> {
        return handler.handle(command)
    }
}