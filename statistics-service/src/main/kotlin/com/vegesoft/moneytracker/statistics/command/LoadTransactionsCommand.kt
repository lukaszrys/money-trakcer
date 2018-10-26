package com.vegesoft.moneytracker.statistics.command

import java.time.LocalDateTime
import java.util.*

class LoadTransactionsCommand(val from: LocalDateTime, val to: LocalDateTime, val accountId: UUID)