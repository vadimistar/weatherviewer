package com.vadimistar.weatherviewer.web.controllers;

import com.vadimistar.weatherviewer.api.dto.CurrentUserDto;
import com.vadimistar.weatherviewer.api.services.LocationService;
import com.vadimistar.weatherviewer.api.services.SessionService;
import com.vadimistar.weatherviewer.web.factory.LocationModelFactory;
import com.vadimistar.weatherviewer.web.models.LocationModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class WebIndexController {
    public static final String FETCH_SAVED_LOCATIONS = "/";
    public static final String SAVE_LOCATION = "/";
    public static final String REMOVE_LOCATION = "/";

    LocationService locationService;
    SessionService sessionService;

    LocationModelFactory savedLocationModelFactory;

    @GetMapping(FETCH_SAVED_LOCATIONS)
    public String fetchSavedLocations(Model model,
                                      @CookieValue(required = false) String sessionId) {
        CurrentUserDto currentUser = sessionService.getCurrentUser(sessionId);

        List<LocationModel> locations;

        if (currentUser.getIsLoggedIn()) {
            locations = locationService.getSavedLocations(currentUser.getId()).stream()
                    .map(savedLocationModelFactory::createLocationModel)
                    .collect(Collectors.toList());
        } else {
            locations = new ArrayList<>();
        }

        model.addAttribute("username", currentUser.getName());
        model.addAttribute("locations", locations);

        return "index";
    }

    @PostMapping(SAVE_LOCATION)
    public String saveLocation(Model model, @CookieValue(required = false) String sessionId,
                                 @RequestParam String name,
                                 @RequestParam Double lat,
                                 @RequestParam Double lon) {
        CurrentUserDto currentUser = sessionService.getCurrentUser(sessionId);

        if (currentUser.getIsLoggedIn()) {
            locationService.saveLocation(currentUser.getId(), name, BigDecimal.valueOf(lat), BigDecimal.valueOf(lon));
        }

        return "redirect:/";
    }

    @DeleteMapping(REMOVE_LOCATION)
    public String removeLocation(Model model, @CookieValue(required = false) String sessionId,
                          @RequestParam Long id) {
        CurrentUserDto currentUser = sessionService.getCurrentUser(sessionId);

        if (currentUser.getIsLoggedIn()) {
            locationService.removeSavedLocation(currentUser.getId(), id);
        }

        return "redirect:/";
    }
}
