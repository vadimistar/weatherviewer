package com.vadimistar.weatherviewer.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionDto {
    @NonNull String id;

    @NonNull Instant expiresAt;
}
