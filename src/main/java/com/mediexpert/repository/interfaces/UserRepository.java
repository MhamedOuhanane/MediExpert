package com.mediexpert.repository.interfaces;

import com.mediexpert.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
