package com.vadimistar.weatherviewer.api.controllers;

import com.vadimistar.weatherviewer.api.dto.CurrentUserDto;
import com.vadimistar.weatherviewer.api.dto.SavedLocationDto;
import com.vadimistar.weatherviewer.api.dto.WeatherDto;
import com.vadimistar.weatherviewer.api.services.LocationService;
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
import org.springframework.beans.propertyeditors.CurrencyEditor;
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
public class SavedLocationsController {

    public static final String FETCH_SAVED_LOCATIONS = "/api/locations/saved";
    public static final String SAVE_LOCATION = "/api/locations/saved";
    public static final String REMOVE_SAVED_LOCATION = "/api/locations/saved/{id}";

    SessionService sessionService;
    LocationService locationService;

    @GetMapping(FETCH_SAVED_LOCATIONS)
    public List<SavedLocationDto> fetchSavedLocations(@CookieValue(defaultValue = "") String sessionId) {
        return sessionService.getCurrentUser(sessionId)
                .map(CurrentUserDto::getId)
                .map(locationService::getSavedLocations)
                .orElse(new ArrayList<>());
    }

    @PostMapping(SAVE_LOCATION)
    public void saveLocation(@CookieValue(defaultValue = "") String sessionId,
                             @RequestParam BigDecimal longitude,
                             @RequestParam BigDecimal latitude,
                             @RequestParam String name) {
        CurrentUserDto currentUser = sessionService.getCurrentUser(sessionId)
                .orElseThrow(() -> new ForbiddenException("session is invalid or expired"));

        locationService.saveLocation(currentUser.getId(), name, latitude, longitude);
    }

    @DeleteMapping(REMOVE_SAVED_LOCATION)
    public void removeSavedLocation(@CookieValue(defaultValue = "") String sessionId,
                                    @PathVariable Long id) {
        sessionService.getCurrentUser(sessionId)
                .ifPresent(user -> locationService.removeSavedLocation(user.getId(), id));
    }
}
