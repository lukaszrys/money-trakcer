package com.vegesoft.moneytracker.statistics.client.request

import java.time.LocalDateTime
import java.util.*

class TransactionRequest(val accountId: UUID,
                         val from: LocalDateTime,
                         val to: LocalDateTime)