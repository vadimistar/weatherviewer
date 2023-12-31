package com.vadimistar.weatherviewer.services;

import com.vadimistar.weatherviewer.dto.api.FoundLocationDto;
import com.vadimistar.weatherviewer.dto.api.SavedLocationDto;
import com.vadimistar.weatherviewer.dto.api.WeatherDto;
import com.vadimistar.weatherviewer.exceptions.BadRequestException;
import com.vadimistar.weatherviewer.exceptions.ForbiddenException;
import com.vadimistar.weatherviewer.exceptions.OpenWeatherApiException;
import com.vadimistar.weatherviewer.factories.api.FoundLocationDtoFactory;
import com.vadimistar.weatherviewer.factories.api.SavedLocationDtoFactory;
import com.vadimistar.weatherviewer.domain.api.GeocodingApiResponse;
import com.vadimistar.weatherviewer.config.OpenWeatherMapConfig;
import com.vadimistar.weatherviewer.store.entity.LocationEntity;
import com.vadimistar.weatherviewer.store.entity.UserEntity;
import com.vadimistar.weatherviewer.store.repositories.LocationRepository;
import com.vadimistar.weatherviewer.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Transactional
public class LocationService {
    LocationRepository locationRepository;
    UserRepository userRepository;

    WeatherService weatherService;

    SavedLocationDtoFactory savedLocationDtoFactory;
    FoundLocationDtoFactory foundLocationDtoFactory;

    OpenWeatherMapConfig openWeatherMapConfig;

    public List<SavedLocationDto> getSavedLocations(Long userId) {
        List<LocationEntity> locations = locationRepository.findAllByUserIdOrderById(userId);

        return locations.stream()
                .map(location -> {
                    WeatherDto weather = weatherService.getWeather(
                            new RestTemplate(),
                            location.getLatitude().doubleValue(),
                            location.getLongitude().doubleValue()
                    );

                    return savedLocationDtoFactory.createLocationDto(location, weather);
                })
                .collect(Collectors.toList());
    }

    public void saveLocation(Long userId, String name, BigDecimal latitude, BigDecimal longitude) {
        if (longitude.doubleValue() < -180.0 || longitude.doubleValue() > 180.0) {
            throw new BadRequestException("invalid longitude");
        }

        if (latitude.doubleValue() < -90.0 || latitude.doubleValue() > 90.0) {
            throw new BadRequestException("invalid latitude");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ForbiddenException("invalid user, please resign in"));

        locationRepository.saveAndFlush(
                LocationEntity.create(name, user, latitude, longitude)
        );
    }

    public void removeSavedLocation(Long userId, Long locationId) {
        locationRepository.removeByIdAndUserId(locationId, userId);
    }

    public List<FoundLocationDto> searchLocations(RestTemplate restTemplate, String query, Integer limit) {
        Map<String, Object> uriVariables = new HashMap<>();

        if (query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        query = query.replace(' ', '_');

        uriVariables.put("q", query);
        uriVariables.put("limit", limit);

        String apiUri = openWeatherMapConfig.getGeocodingUri(uriVariables);

        try {
            List<GeocodingApiResponse> apiResponse = restTemplate
                    .exchange(
                            apiUri,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<GeocodingApiResponse>>() {
                            }
                    )
                    .getBody();

            return Objects.requireNonNull(apiResponse).stream()
                    .map(foundLocationDtoFactory::createFoundLocationDto)
                    .collect(Collectors.toList());

        } catch (HttpClientErrorException e) {
            throw new OpenWeatherApiException(e.getStatusText());
        }
    }
}
