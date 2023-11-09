package com.vadimistar.weatherviewer.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoundLocationDto {
    @NonNull
    String name;

    @NonNull
    Double lat;

    @NonNull
    Double lon;
}
