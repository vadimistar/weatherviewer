package com.vadimistar.weatherviewer.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrentUserDto {
    @JsonProperty("is_logged_in")
    Boolean isLoggedIn;

    @NonNull
    Long id;

    @NonNull
    @Builder.Default
    String name = "";
}
