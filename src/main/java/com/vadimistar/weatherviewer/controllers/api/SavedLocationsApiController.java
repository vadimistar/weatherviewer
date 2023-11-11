package com.vadimistar.weatherviewer.controllers.api;

import com.vadimistar.weatherviewer.dto.api.CurrentUserDto;
import com.vadimistar.weatherviewer.dto.api.SavedLocationDto;
import com.vadimistar.weatherviewer.services.LocationService;
import com.vadimistar.weatherviewer.exceptions.ForbiddenException;
import com.vadimistar.weatherviewer.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class SavedLocationsApiController {

    public static final String FETCH_SAVED_LOCATIONS = "/api/locations/saved";
    public static final String SAVE_LOCATION = "/api/locations/saved";
    public static final String REMOVE_SAVED_LOCATION = "/api/locations/saved/{id}";

    SessionService sessionService;
    LocationService locationService;

    @GetMapping(FETCH_SAVED_LOCATIONS)
    public List<SavedLocationDto> fetchSavedLocations(@CookieValue(defaultValue = "") String sessionId) {
        CurrentUserDto currentUser = sessionService.getCurrentUser(sessionId);

        if (currentUser.getIsLoggedIn()) {
            return locationService.getSavedLocations(currentUser.getId());
        }

        return new ArrayList<>();
    }

    @PostMapping(SAVE_LOCATION)
    public void saveLocation(@CookieValue(defaultValue = "") String sessionId,
                             @RequestParam BigDecimal longitude,
                             @RequestParam BigDecimal latitude,
                             @RequestParam String name) {
        CurrentUserDto currentUser = sessionService.getCurrentUser(sessionId);

        if (currentUser.getIsLoggedIn()) {
            locationService.saveLocation(currentUser.getId(), name, latitude, longitude);
        }

        throw new ForbiddenException("session is invalid or expired");
    }

    @DeleteMapping(REMOVE_SAVED_LOCATION)
    public void removeSavedLocation(@CookieValue(defaultValue = "") String sessionId,
                                    @PathVariable Long id) {
        CurrentUserDto currentUser = sessionService.getCurrentUser(sessionId);

        if (currentUser.getIsLoggedIn()) {
            locationService.removeSavedLocation(currentUser.getId(), id);
        }
    }
}
