package com.vadimistar.weatherviewer.web.controllers;

import com.vadimistar.weatherviewer.api.dto.SavedLocationDto;
import com.vadimistar.weatherviewer.api.services.LocationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class WebIndexController {
    public static final String INDEX = "/";

    LocationService locationService;

    @GetMapping(INDEX)
    public String index(Model model) {
        List<SavedLocationDto> locations = locationService.getSavedLocations(0L);

        model.addAttribute("username", "vadi1234");
        model.addAttribute("locations", locations);

        return "index";
    }
}
