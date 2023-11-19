package com.vadimistar.weatherviewer.services;

import com.password4j.Password;
import com.vadimistar.weatherviewer.exceptions.UserAlreadyExistsException;
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
        if (userRepository.existsByName(name)) {
            throw new UserAlreadyExistsException();
        }

        String encodedPassword = Password.hash(password).withBcrypt().getResult();

        userRepository.saveAndFlush(UserEntity.create(name, encodedPassword));
    }
}
