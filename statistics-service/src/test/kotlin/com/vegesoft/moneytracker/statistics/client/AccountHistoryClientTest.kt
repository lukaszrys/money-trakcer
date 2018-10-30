package com.vegesoft.moneytracker.statistics.client

import com.vegesoft.moneytracker.statistics.client.request.TransactionRequest
import com.vegesoft.moneytracker.statistics.configuration.properties.WebClientProperties
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
internal class AccountHistoryClientTest {

    @RelaxedMockK
    lateinit var webClient: WebClient
    @RelaxedMockK
    lateinit var webClientProperties: WebClientProperties
    @InjectMockKs
    lateinit var accountHistoryClient: RestAsyncAccountHistoryClient

    @Test
    @DisplayName("Should everything go smoothly")
    fun handle() {
        //Given
        val transactionRequest = TransactionRequest(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now())
        //When
        accountHistoryClient.findTransactionViews(transactionRequest).blockFirst()
        //Then
        verify { webClient.get() }
    }
}