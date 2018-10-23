package com.vegesoft.moneytracker.accounthistory.rest

import com.vegesoft.moneytracker.accounthistory.command.AddIncomeCommand
import com.vegesoft.moneytracker.accounthistory.domain.Income
import com.vegesoft.moneytracker.accounthistory.domain.repository.IncomeRepository
import com.vegesoft.moneytracker.accounthistory.query.view.TransactionView
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IncomeControllerIT extends Specification {

    private static final String API = "/api/incomes"

    @Autowired
    private WebTestClient webTestClient
    @Autowired
    private IncomeRepository incomeRepository

    @Test
    def "shouldSave_income"() {
        given: "create command"
            def command = new AddIncomeCommand(BigDecimal.TEN, UUID.randomUUID(), LocalDateTime.now())
        when: "sending request"
            def exchange = webTestClient.post()
                    .uri(API)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .body(Mono.just(command), AddIncomeCommand.class)
                    .exchange()
        then: "request processed correctly"
            exchange
                    .expectStatus()
                    .is2xxSuccessful()
            StepVerifier.create(incomeRepository.findAll()).assertNext { income -> income != null }
                    .expectComplete().verify()
        cleanup: "clean database"
            incomeRepository.deleteAll().block()
    }
}
