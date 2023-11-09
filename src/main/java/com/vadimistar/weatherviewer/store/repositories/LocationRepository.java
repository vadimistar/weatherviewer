package com.vadimistar.weatherviewer.store.repositories;

import com.vadimistar.weatherviewer.store.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    List<LocationEntity> findAllByUserIdOrderById(Long userId);

    void removeByIdAndUserId(Long id, Long userId);
}
