package com.mediexpert.service.impl;

import com.mediexpert.model.User;
import com.mediexpert.repository.interfaces.UserRepository;
import com.mediexpert.service.interfaces.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean checkEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
