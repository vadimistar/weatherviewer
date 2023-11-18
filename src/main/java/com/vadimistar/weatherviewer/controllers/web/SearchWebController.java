package com.vadimistar.weatherviewer.controllers.web;

import com.vadimistar.weatherviewer.dto.api.CurrentUserDto;
import com.vadimistar.weatherviewer.dto.api.WeatherDto;
import com.vadimistar.weatherviewer.services.LocationService;
import com.vadimistar.weatherviewer.services.SessionService;
import com.vadimistar.weatherviewer.services.WeatherService;
import com.vadimistar.weatherviewer.factories.web.LocationModelFactory;
import com.vadimistar.weatherviewer.domain.web.LocationModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class SearchWebController {
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

        List<LocationModel> locations = locationService.searchLocations(new RestTemplate(), query, SEARCH_LIMIT)
                .stream()
                .map(location -> {
                    WeatherDto weather = weatherService.getWeather(new RestTemplate(), location.getLat(), location.getLon());
                    return locationModelFactory.createLocationModel(location, weather);
                })
                .collect(Collectors.toList());

        model.addAttribute("username", currentUser.getName());
        model.addAttribute("locations", locations);

        return "search";
    }
}
