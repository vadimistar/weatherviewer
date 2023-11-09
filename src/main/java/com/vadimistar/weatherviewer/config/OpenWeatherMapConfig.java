package com.vadimistar.weatherviewer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Configuration
public class OpenWeatherMapConfig {
    public static final String APPID = "appid";

    @Value("${open-weather-map-api.key}")
    private String apiKey;

    @Value("${open-weather-map-api.base-url}")
    private String baseUrl;

    public <T> T get(Class<T> responseType, Map<String, Object> uriVariables) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

        uriVariables.forEach(uriComponentsBuilder::queryParam);

        uriComponentsBuilder.queryParam(APPID, apiKey);

        return new RestTemplate().getForObject(
                uriComponentsBuilder.toUriString(),
                responseType
        );
    }
}
