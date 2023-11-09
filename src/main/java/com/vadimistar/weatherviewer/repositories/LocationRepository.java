package com.vadimistar.weatherviewer.repositories;

import com.vadimistar.weatherviewer.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    List<LocationEntity> findAllByUserIdOrderById(Long userId);

    void removeByIdAndUserId(Long id, Long userId);
}
