package com.vadimistar.weatherviewer.controllers;

import com.vadimistar.weatherviewer.dto.LocationDto;
import com.vadimistar.weatherviewer.dto.SessionUserDto;
import com.vadimistar.weatherviewer.entity.LocationEntity;
import com.vadimistar.weatherviewer.entity.UserEntity;
import com.vadimistar.weatherviewer.exceptions.BadRequestException;
import com.vadimistar.weatherviewer.exceptions.ForbiddenException;
import com.vadimistar.weatherviewer.factory.LocationDtoFactory;
import com.vadimistar.weatherviewer.repositories.LocationRepository;
import com.vadimistar.weatherviewer.repositories.UserRepository;
import com.vadimistar.weatherviewer.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
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

    LocationDtoFactory locationDtoFactory;
    SessionService sessionService;
    LocationRepository locationRepository;
    UserRepository userRepository;

    @GetMapping(FETCH_SAVED_LOCATIONS)
    public List<LocationDto> fetchSavedLocations(@CookieValue(defaultValue = "") String sessionId) {
        Optional<SessionUserDto> sessionUser = sessionService.getUserBySession(sessionId);

        if (!sessionUser.isPresent()) {
            return new ArrayList<>();
        }

        List<LocationEntity> locations = locationRepository.findAllByUserIdOrderById(sessionUser.get().getId());

        // TODO: fetch weather information from api

        return locations.stream()
                .map(locationDtoFactory::createLocationDto)
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
