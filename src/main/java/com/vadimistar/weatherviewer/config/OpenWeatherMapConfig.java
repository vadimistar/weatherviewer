package com.vadimistar.weatherviewer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Configuration
public class OpenWeatherMapConfig {
    public static final String APPID = "appid";

    @Value("${open-weather-map-api.key}")
    private String apiKey;

    @Value("${open-weather-map-api.weather.base-url}")
    private String weatherBaseUrl;

    @Value("${open-weather-map-api.geocoding.base-url}")
    private String geocodingBaseUrl;

    public String getWeatherUri(Map<String, Object> uriVariables) {
        return getUri(uriVariables, weatherBaseUrl);
    }

    public String getGeocodingUri(Map<String, Object> uriVariables) {
        return getUri(uriVariables, geocodingBaseUrl);
    }

    private String getUri(Map<String, Object> uriVariables, String baseUrl) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

        uriVariables.forEach(uriComponentsBuilder::queryParam);

        uriComponentsBuilder.queryParam(APPID, apiKey);

        return uriComponentsBuilder.toUriString();
    }
}
