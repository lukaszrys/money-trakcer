package com.vegesoft.moneytracker.statistics.handler

import com.vegesoft.moneytracker.statistics.client.AccountHistoryClient
import com.vegesoft.moneytracker.statistics.domain.AccountStatistic
import com.vegesoft.moneytracker.statistics.domain.repository.AccountStatisticRepository
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ReactiveLoadTransactionsHandlerTest {

    @MockK
    lateinit var accountHistoryClient: AccountHistoryClient
    @MockK
    lateinit var accountStatisticsRepository: AccountStatisticRepository
    @InjectMockKs
    lateinit var reactiveLoadTransactionsHandler: ReactiveLoadTransactionsHandler

    @Test
    fun handle() {

    }
}