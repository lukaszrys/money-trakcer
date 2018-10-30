package com.vegesoft.moneytracker.statistics.configuration.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class WebClientProperties(@Value("\${rest-client.connect-timeout-ms}") val connectTimeoutMs: Int,
                          @Value("\${rest-client.account-history.uri}") val accountHistoryUri: String)