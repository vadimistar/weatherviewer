package com.vadimistar.weatherviewer.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {
    @NonNull
    Long id;

    @NonNull
    String name;

    @NonNull
    @Builder.Default
    String weather = "";

    BigDecimal temperature;
}
