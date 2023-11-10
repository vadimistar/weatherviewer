package com.vadimistar.weatherviewer.api.controllers;

import com.vadimistar.weatherviewer.api.dto.SavedLocationDto;
import com.vadimistar.weatherviewer.api.dto.SessionUserDto;
import com.vadimistar.weatherviewer.api.dto.WeatherDto;
import com.vadimistar.weatherviewer.store.entity.LocationEntity;
import com.vadimistar.weatherviewer.store.entity.UserEntity;
import com.vadimistar.weatherviewer.api.exceptions.BadRequestException;
import com.vadimistar.weatherviewer.api.exceptions.ForbiddenException;
import com.vadimistar.weatherviewer.api.factory.SavedLocationDtoFactory;
import com.vadimistar.weatherviewer.store.repositories.LocationRepository;
import com.vadimistar.weatherviewer.store.repositories.UserRepository;
import com.vadimistar.weatherviewer.api.services.SessionService;
import com.vadimistar.weatherviewer.api.services.WeatherService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class LocationsSavedController {

    public static final String FETCH_SAVED_LOCATIONS = "/api/locations/saved";
    public static final String SAVE_LOCATION = "/api/locations/saved";
    public static final String REMOVE_SAVED_LOCATION = "/api/locations/saved/{id}";

    SavedLocationDtoFactory locationDtoFactory;
    SessionService sessionService;
    LocationRepository locationRepository;
    UserRepository userRepository;
    WeatherService weatherService;

    @GetMapping(FETCH_SAVED_LOCATIONS)
    public List<SavedLocationDto> fetchSavedLocations(@CookieValue(defaultValue = "") String sessionId) {
        // todo: move sessionId cookie value parameter somewhere
        Optional<SessionUserDto> sessionUser = sessionService.getUserBySession(sessionId);

        if (!sessionUser.isPresent()) {
            return new ArrayList<>();
        }

        List<LocationEntity> locations = locationRepository.findAllByUserIdOrderById(sessionUser.get().getId());

        return locations.stream()
                .map(location -> {
                    WeatherDto weather = weatherService.getWeather(
                            location.getLatitude().doubleValue(),
                            location.getLongitude().doubleValue()
                    );

                    return locationDtoFactory.createLocationDto(location, weather);
                })
                .collect(Collectors.toList());
    }

    @PostMapping(SAVE_LOCATION)
    public void saveLocation(@CookieValue(defaultValue = "") String sessionId,
                             @RequestParam BigDecimal longitude,
                             @RequestParam BigDecimal latitude,
                             @RequestParam String name) {
        if (longitude.doubleValue() < -180.0 || longitude.doubleValue() > 180.0) {
            throw new BadRequestException("invalid longitude");
        }

        if (latitude.doubleValue() < -90.0 || latitude.doubleValue() > 90.0) {
            throw new BadRequestException("invalid latitude");
        }

        SessionUserDto sessionUser = sessionService.getUserBySession(sessionId)
                .orElseThrow(() -> new ForbiddenException("session is invalid or expired"));

        UserEntity user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new ForbiddenException("invalid user, please resign in"));

        locationRepository.saveAndFlush(
                LocationEntity.create(name, user, latitude, longitude)
        );
    }

    @DeleteMapping(REMOVE_SAVED_LOCATION)
    public void removeSavedLocation(@CookieValue(defaultValue = "") String sessionId,
                                    @PathVariable Long id) {
        Optional<SessionUserDto> sessionUser = sessionService.getUserBySession(sessionId);

        sessionUser.ifPresent(
                sessionUserDto -> locationRepository.removeByIdAndUserId(id, sessionUserDto.getId())
        );
    }
}
