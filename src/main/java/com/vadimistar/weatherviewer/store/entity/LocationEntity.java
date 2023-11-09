package com.vadimistar.weatherviewer.store.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "locations", indexes = {
        @Index(name = "user_id_index", columnList = "user_id")
})
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @NonNull
    String name;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;

    @NonNull
    BigDecimal latitude;

    @NonNull
    BigDecimal longitude;

    public static LocationEntity create(String name, UserEntity user, BigDecimal latitude, BigDecimal longitude) {
        return builder()
                .name(name)
                .user(user)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
