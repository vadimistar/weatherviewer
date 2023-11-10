package com.vadimistar.weatherviewer.web.controllers;

import com.vadimistar.weatherviewer.api.dto.CurrentUserDto;
import com.vadimistar.weatherviewer.api.dto.SavedLocationDto;
import com.vadimistar.weatherviewer.api.factory.CurrentUserDtoFactory;
import com.vadimistar.weatherviewer.api.services.LocationService;
import com.vadimistar.weatherviewer.api.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class WebIndexController {
    public static final String INDEX = "/";

    LocationService locationService;
    SessionService sessionService;

    @GetMapping(INDEX)
    public String index(Model model,
                        @CookieValue(required = false) String sessionId) {
        CurrentUserDto currentUser = sessionService.getCurrentUser(sessionId);

        List<SavedLocationDto> locations;

        if (currentUser.getIsLoggedIn()) {
            locations = locationService.getSavedLocations(currentUser.getId());
        } else {
            locations = new ArrayList<>();
        }

        model.addAttribute("username", currentUser.getName());
        model.addAttribute("locations", locations);

        return "index";
    }
}
