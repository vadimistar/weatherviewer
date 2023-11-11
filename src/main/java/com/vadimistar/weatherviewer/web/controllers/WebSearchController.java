package com.vadimistar.weatherviewer.web.controllers;

import com.vadimistar.weatherviewer.api.dto.CurrentUserDto;
import com.vadimistar.weatherviewer.api.dto.FoundLocationDto;
import com.vadimistar.weatherviewer.api.dto.WeatherDto;
import com.vadimistar.weatherviewer.api.services.LocationService;
import com.vadimistar.weatherviewer.api.services.SessionService;
import com.vadimistar.weatherviewer.api.services.WeatherService;
import com.vadimistar.weatherviewer.web.factory.LocationModelFactory;
import com.vadimistar.weatherviewer.web.models.LocationModel;
import com.vadimistar.weatherviewer.web.models.SearchModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class WebSearchController {
    public static final String SEARCH = "/search";
    public static final Integer SEARCH_LIMIT = 5;

    SessionService sessionService;
    LocationService locationService;
    WeatherService weatherService;

    LocationModelFactory locationModelFactory;

    @GetMapping(SEARCH)
    public String search(Model model,
                         @CookieValue(required = false) String sessionId,
                         @RequestParam String query) {
        CurrentUserDto currentUser = sessionService.getCurrentUser(sessionId);

        List<LocationModel> locations = locationService.searchLocations(query, SEARCH_LIMIT)
                .stream()
                .map(location -> {
                    WeatherDto weather = weatherService.getWeather(location.getLat(), location.getLon());
                    return locationModelFactory.createLocationModel(location, weather);
                })
                .collect(Collectors.toList());

        model.addAttribute("username", currentUser.getName());
        model.addAttribute("locations", locations);

        return "search";
    }
}
