package com.vadimistar.weatherviewer.domain.web;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterModel {
    String name;
    String password;
    String confirmPassword;
}
