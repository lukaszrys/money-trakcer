package com.vegesoft.moneytracker.statistics.handler.mapper

import com.vegesoft.moneytracker.statistics.command.LoadTransactionsCommand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

internal class LoadTransactionCommandMapperTest {

    private val loadTransactionCommandMapper = LoadTransactionCommandMapper()

    @Test
    @DisplayName("Should map command to request")
    fun shouldMapCommand_toRequest() {
        //Given
        val from = LocalDateTime.now()
        val to = LocalDateTime.now()
        val accountId = UUID.randomUUID()
        val command = LoadTransactionsCommand(from, to, accountId)
        //When
        val mapToTransactionRequest = loadTransactionCommandMapper.mapToTransactionRequest(command)
        //Then
        assertEquals(from, mapToTransactionRequest.from)
        assertEquals(to, mapToTransactionRequest.to)
        assertEquals(accountId, mapToTransactionRequest.accountId)
    }
}
