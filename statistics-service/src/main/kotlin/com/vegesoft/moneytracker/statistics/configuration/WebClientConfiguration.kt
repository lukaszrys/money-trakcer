package com.vegesoft.moneytracker.statistics.configuration

import com.vegesoft.moneytracker.statistics.configuration.properties.WebClientProperties
import io.netty.channel.ChannelOption
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration(private val webClientProperties: WebClientProperties) {
    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(clientHttpConnector())
                .build()
    }

    private fun clientHttpConnector(): ReactorClientHttpConnector {
        return ReactorClientHttpConnector { options ->
            options.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientProperties.connectTimeoutMs)
                    .build()
        }
    }
}