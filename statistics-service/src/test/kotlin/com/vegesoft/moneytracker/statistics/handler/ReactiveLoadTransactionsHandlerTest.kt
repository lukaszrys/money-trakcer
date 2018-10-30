package com.vegesoft.moneytracker.statistics.handler

import com.vegesoft.moneytracker.statistics.client.AccountHistoryClient
import com.vegesoft.moneytracker.statistics.client.request.TransactionRequest
import com.vegesoft.moneytracker.statistics.client.response.TransactionResponse
import com.vegesoft.moneytracker.statistics.command.LoadTransactionsCommand
import com.vegesoft.moneytracker.statistics.domain.AccountStatistic
import com.vegesoft.moneytracker.statistics.domain.AccountStatisticRange
import com.vegesoft.moneytracker.statistics.domain.repository.AccountStatisticRepository
import com.vegesoft.moneytracker.statistics.handler.mapper.LoadTransactionCommandMapper
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
internal class ReactiveLoadTransactionsHandlerTest {

    @MockK
    lateinit var accountHistoryClient: AccountHistoryClient
    @MockK
    lateinit var accountStatisticsRepository: AccountStatisticRepository
    @MockK
    lateinit var loadTransactionCommandMapper: LoadTransactionCommandMapper
    @InjectMockKs
    lateinit var reactiveLoadTransactionsHandler: ReactiveLoadTransactionsHandler

    @Test
    @DisplayName("Should handle command")
    fun handle() {
        //Given
        val accountId = UUID.randomUUID()
        val from = LocalDateTime.now()
        val to = LocalDateTime.now()
        val transactionRequest = TransactionRequest(accountId, from, to)
        val transactionResponse = TransactionResponse(BigDecimal.TEN, "food", LocalDateTime.now(), accountId)
        val loadTransactionsCommand = LoadTransactionsCommand(LocalDateTime.now(), LocalDateTime.now(), accountId)
        val accountStatistic = AccountStatistic(UUID.randomUUID(), AccountStatisticRange(from, to),
                accountId, BigDecimal.TEN, mutableListOf(), mutableListOf())

        every { loadTransactionCommandMapper.mapToTransactionRequest(loadTransactionsCommand) } returns transactionRequest
        every { accountHistoryClient.findTransactionViews(transactionRequest) } returns Flux.just(transactionResponse)
        every { accountStatisticsRepository.save(any<AccountStatistic>()) } returns Mono.just(accountStatistic)
        //When
        val handle = reactiveLoadTransactionsHandler.handle(loadTransactionsCommand)
        //Then
        StepVerifier.create(handle).expectComplete().verify()
        verify { accountHistoryClient.findTransactionViews(transactionRequest) }
        verify { loadTransactionCommandMapper.mapToTransactionRequest(loadTransactionsCommand) }
        verify { accountStatisticsRepository.save(any<AccountStatistic>()) }
    }
}