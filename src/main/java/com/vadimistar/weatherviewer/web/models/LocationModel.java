package com.vadimistar.weatherviewer.web.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationModel {
    Long id;

    String name;

    String time;

    String iconUrl;

    String weather;

    Integer temperature;

    Double lat;

    Double lon;
}
