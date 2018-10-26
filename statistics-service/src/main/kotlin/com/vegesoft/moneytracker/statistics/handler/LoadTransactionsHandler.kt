package com.vegesoft.moneytracker.statistics.handler

import com.vegesoft.moneytracker.statistics.command.LoadTransactionsCommand

interface LoadTransactionsHandler {
    
    fun handle(command: LoadTransactionsCommand)
}