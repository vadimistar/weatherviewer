package com.vadimistar.weatherviewer.web.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationModel {
    String name;

    String time;

    String iconUrl;

    String weather;

    Integer temperature;
}
