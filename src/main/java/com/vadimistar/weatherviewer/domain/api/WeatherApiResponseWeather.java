package com.vadimistar.weatherviewer.domain.api;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiResponseWeather {
    String main;

    String icon;
}
