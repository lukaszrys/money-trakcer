package com.vegesoft.moneytracker.statistics.rest

import com.vegesoft.moneytracker.statistics.command.LoadTransactionsCommand
import com.vegesoft.moneytracker.statistics.configuration.properties.WebClientProperties
import com.vegesoft.moneytracker.statistics.domain.AccountStatistic
import com.vegesoft.moneytracker.statistics.domain.repository.AccountStatisticRepository
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONException
import org.json.JSONObject
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.io.IOException
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import java.util.function.Consumer

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("IT")
internal class AccountStatisticsControllerIT {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var webClientProperties: WebClientProperties

    @Autowired
    private lateinit var accountStatisticsRepository: AccountStatisticRepository

    private lateinit var server: MockWebServer

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        val accountHistoryUri = webClientProperties.accountHistoryUri
        server.start(Integer.valueOf(accountHistoryUri.substring(accountHistoryUri.indexOf("t:") + 2)))
    }

    @AfterEach
    @Throws(IOException::class)
    fun cleanUp() {
        accountStatisticsRepository.deleteAll().block()
        server.shutdown()
    }

    @Test
    @DisplayName("Should process data and create statistic")
    fun shouldProcess_createStatistics() {
        //Given
        val amount = BigDecimal.TEN
        val accountId = UUID.randomUUID()
        val from = LocalDateTime.now()
        val to = LocalDateTime.now()
        prepareResponseFromAccountHistory(amount, accountId)
        val command = LoadTransactionsCommand(from, to, accountId)
        //When
        val exchange = webTestClient.post()
                .uri("/api/statistics")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(command), LoadTransactionsCommand::class.java)
                .exchange()
        //Then
        exchange.expectStatus().is2xxSuccessful

        StepVerifier.create<AccountStatistic>(accountStatisticsRepository.findAll())
                .assertNext { statistic ->
                    run {
                        Assertions.assertEquals(statistic.amount, amount)
                        Assertions.assertEquals(statistic.expenses.size, 1)
                        Assertions.assertEquals(statistic.incomes.size, 1)
                        Assertions.assertEquals(statistic.range.from, from)
                        Assertions.assertEquals(statistic.range.to, to)
                        Assertions.assertEquals(statistic.accountId, accountId)
                    }
                }
                .expectComplete().verify()
    }

    @Throws(JSONException::class)
    private fun prepareResponseFromAccountHistory(amount: BigDecimal, accountId: UUID) {
        val json = JSONObject()
        json.put("amount", amount.toString())
        json.put("type", "food")
        json.put("createdAt", LocalDateTime.now().toString())
        json.put("accountId", accountId.toString())
        prepareResponse(Consumer { response -> response.setHeader("Content-Type", "application/json").setBody(json.toString()) })
    }

    private fun prepareResponse(consumer: Consumer<MockResponse>) {
        val response = MockResponse()
        consumer.accept(response)
        this.server.enqueue(response)
    }
}