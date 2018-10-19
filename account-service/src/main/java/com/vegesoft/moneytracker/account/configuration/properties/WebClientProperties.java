package com.vegesoft.moneytracker.account.configuration.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientProperties {

    private final int connectTimeoutMs;
    private final String accountHistoryUri;

    public WebClientProperties(@Value("${rest-client.connect-timeout-ms}") final int connectTimeoutMs,
        @Value("${rest-client.account-history.uri}") final String accountHistoryUri) {
        this.connectTimeoutMs = connectTimeoutMs;
        this.accountHistoryUri = accountHistoryUri;
    }

    public int getConnectTimeoutMs() {
        return connectTimeoutMs;
    }

    public String getAccountHistoryUri() {
        return accountHistoryUri;
    }
}
