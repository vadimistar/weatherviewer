package com.vadimistar.weatherviewer.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionUserDto {
    @NonNull
    Long id;

    @NonNull
    String name;
}
