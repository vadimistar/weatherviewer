package com.vadimistar.weatherviewer.store.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users", indexes = {
        @Index(name = "name_index", columnList = "name", unique = true)
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @NonNull
    String name;

    @NonNull
    String password;

    public static UserEntity create(String name, String password) {
        return builder()
                .name(name)
                .password(password)
                .build();
    }
}
