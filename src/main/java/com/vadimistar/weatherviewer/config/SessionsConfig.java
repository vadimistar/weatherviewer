package com.vadimistar.weatherviewer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Configuration
public class SessionsConfig {
    @Value("${sessions.lifetime}")
    private Duration lifetime;

}
