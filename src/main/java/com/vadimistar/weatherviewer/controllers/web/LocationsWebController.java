package com.vadimistar.weatherviewer.controllers.web;

import com.vadimistar.weatherviewer.dto.api.CurrentUserDto;
import com.vadimistar.weatherviewer.services.LocationService;
import com.vadimistar.weatherviewer.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class LocationsWebController {
    public static final String ADD_LOCATION = "/locations/add";
    public static final String REMOVE_LOCATION = "/locations/remove";

    SessionService sessionService;
    LocationService locationService;

    @PostMapping(ADD_LOCATION)
    public String saveLocation(@CookieValue(required = false) String sessionId,
                               @RequestParam String name,
                               @RequestParam Double lat,
                               @RequestParam Double lon) {
        CurrentUserDto currentUser = sessionService.getCurrentUser(sessionId);

        if (currentUser.getIsLoggedIn()) {
            locationService.saveLocation(currentUser.getId(), name, BigDecimal.valueOf(lat), BigDecimal.valueOf(lon));
        }

        return "redirect:/";
    }

    @PostMapping(REMOVE_LOCATION)
    public String removeLocation(@CookieValue(required = false) String sessionId,
                                 @RequestParam Long id) {
        CurrentUserDto currentUser = sessionService.getCurrentUser(sessionId);

        if (currentUser.getIsLoggedIn()) {
            locationService.removeSavedLocation(currentUser.getId(), id);
        }

        return "redirect:/";
    }
}
