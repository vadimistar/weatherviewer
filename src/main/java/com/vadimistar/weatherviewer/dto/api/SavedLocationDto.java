package com.vadimistar.weatherviewer.dto.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.ZoneOffset;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SavedLocationDto {
    @NonNull
    Long id;

    @NonNull
    String name;

    @NonNull
    @Builder.Default
    String weather = "";

    @NonNull
    @Builder.Default
    String iconUrl = "";

    @NonNull
    BigDecimal temperature;

    @NonNull
    ZoneOffset zoneOffset;
}
