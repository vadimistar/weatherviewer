package com.vadimistar.weatherviewer.api.services;

import com.vadimistar.weatherviewer.api.exceptions.BadRequestException;
import com.vadimistar.weatherviewer.store.entity.UserEntity;
import com.vadimistar.weatherviewer.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    public void createUser(String name, String password) {
        if (!userRepository.existsByName(name)) {
            throw new BadRequestException("user with the specified name already exists");
        }

        userRepository.saveAndFlush(UserEntity.create(name, password));
    }
}
