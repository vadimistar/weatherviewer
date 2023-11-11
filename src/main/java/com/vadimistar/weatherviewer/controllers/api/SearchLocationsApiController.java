package com.vadimistar.weatherviewer.controllers.api;

import com.vadimistar.weatherviewer.dto.api.FoundLocationDto;
import com.vadimistar.weatherviewer.services.LocationService;
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
public class SearchLocationsApiController {
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
