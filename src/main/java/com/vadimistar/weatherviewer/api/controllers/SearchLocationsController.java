package com.vadimistar.weatherviewer.api.controllers;

import com.vadimistar.weatherviewer.api.dto.FoundLocationDto;
import com.vadimistar.weatherviewer.api.factory.FoundLocationDtoFactory;
import com.vadimistar.weatherviewer.api.model.GeocodingApiResponse;
import com.vadimistar.weatherviewer.config.OpenWeatherMapConfig;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchLocationsController {
    public static final String SEARCH_LOCATIONS = "/api/locations/search";
    public static final String DEFAULT_SEARCH_LIMIT = "5";

    OpenWeatherMapConfig openWeatherMapConfig;
    FoundLocationDtoFactory foundLocationDtoFactory;

    @GetMapping(SEARCH_LOCATIONS)
    public List<FoundLocationDto> searchLocations(
            @RequestParam String query,
            @RequestParam(defaultValue = DEFAULT_SEARCH_LIMIT) Integer limit){
        Map<String, Object> uriVariables = new HashMap<>();

        query = query.replace(' ', '_');

        uriVariables.put("q", query);
        uriVariables.put("limit", limit);

        String apiUri = openWeatherMapConfig.getGeocodingUri(uriVariables);
        List<GeocodingApiResponse> apiResponse = new RestTemplate()
                .exchange(
                        apiUri,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<GeocodingApiResponse>>() {
                        }
                )
                .getBody();

        assert apiResponse != null;
        return apiResponse.stream()
                .map(foundLocationDtoFactory::createFoundLocationDto)
                .collect(Collectors.toList());
    }
}
