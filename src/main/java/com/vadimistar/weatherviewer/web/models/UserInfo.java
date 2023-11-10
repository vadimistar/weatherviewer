package com.vadimistar.weatherviewer.web.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfo {
    String name;
    String password;
}
