package com.vadimistar.weatherviewer.web.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SavedLocationModel {
    String name;

    String time;

    String iconUrl;

    String weather;

    Integer temperature;
}
