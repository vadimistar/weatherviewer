package com.vadimistar.weatherviewer.web.factory;

import com.vadimistar.weatherviewer.api.dto.SavedLocationDto;
import com.vadimistar.weatherviewer.web.models.SavedLocationModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SavedLocationModelFactory {
    public SavedLocationModel createSavedLocationModel(SavedLocationDto savedLocationDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                savedLocationDto.getTime(),
                savedLocationDto.getZoneOffset()
        );
        String formattedTime = localDateTime.format(formatter);

        return SavedLocationModel.builder()
                .name(savedLocationDto.getName())
                .temperature(savedLocationDto.getTemperature().intValue())
                .weather(savedLocationDto.getWeather())
                .iconUrl(savedLocationDto.getIconUrl())
                .time(formattedTime)
                .build();
    }
}
