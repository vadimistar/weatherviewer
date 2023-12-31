package com.vadimistar.weatherviewer.controllers.web;

import com.vadimistar.weatherviewer.dto.api.CurrentUserDto;
import com.vadimistar.weatherviewer.services.LocationService;
import com.vadimistar.weatherviewer.services.SessionService;
import com.vadimistar.weatherviewer.factories.web.LocationModelFactory;
import com.vadimistar.weatherviewer.domain.web.LocationModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class IndexWebController {
    public static final String INDEX = "/";

    LocationService locationService;
    SessionService sessionService;

    LocationModelFactory savedLocationModelFactory;

    @GetMapping(INDEX)
    public String index(Model model,
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
}
