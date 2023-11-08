package com.vadimistar.weatherviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrentUserDto {
    @JsonProperty("is_logged_in")
    Boolean isLoggedIn;

    String name;
}
