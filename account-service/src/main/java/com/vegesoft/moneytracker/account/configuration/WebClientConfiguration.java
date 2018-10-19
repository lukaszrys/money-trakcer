package com.vegesoft.moneytracker.account.configuration;

import com.vegesoft.moneytracker.account.configuration.properties.WebClientProperties;
import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    private final WebClientProperties webClientProperties;

    public WebClientConfiguration(final WebClientProperties webClientProperties) {
        this.webClientProperties = webClientProperties;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(clientHttpConnector())
            .build();
    }

    private ReactorClientHttpConnector clientHttpConnector() {
        return new ReactorClientHttpConnector(
            options -> options.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientProperties.getConnectTimeoutMs())
                .build());
    }
}
