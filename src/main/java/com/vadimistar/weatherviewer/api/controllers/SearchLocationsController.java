package com.vadimistar.weatherviewer.api.controllers;

import com.vadimistar.weatherviewer.api.dto.FoundLocationDto;
import com.vadimistar.weatherviewer.api.services.LocationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchLocationsController {
    public static final String SEARCH_LOCATIONS = "/api/locations/search";
    public static final String DEFAULT_SEARCH_LIMIT = "5";

    LocationService locationService;

    @GetMapping(SEARCH_LOCATIONS)
    public List<FoundLocationDto> searchLocations(
            @RequestParam String query,
            @RequestParam(defaultValue = DEFAULT_SEARCH_LIMIT) Integer limit){
        return locationService.searchLocations(query, limit);
    }
}
