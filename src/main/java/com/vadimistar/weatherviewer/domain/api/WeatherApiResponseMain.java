package com.vadimistar.weatherviewer.domain.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherApiResponseMain {
    Double temp;
}
