package com.mediexpert.service.impl;

import com.mediexpert.model.User;
import com.mediexpert.repository.interfaces.UserRepository;
import com.mediexpert.service.interfaces.SpecialisteService;
import com.mediexpert.service.interfaces.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SpecialisteService specialisteService;

    public UserServiceImpl (UserRepository userRepository, SpecialisteService specialisteService) {
        this.userRepository = userRepository;
        this.specialisteService = specialisteService;
    }

    @Override
    public Boolean checkEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public Object login(String email, String password) {
        if (email == null || password == null) throw new IllegalArgumentException("Adresse e-mail ou mot de passe incorrect");
        try {
            if (!checkEmail(email)) throw new IllegalArgumentException("Adresse e-mail ou mot de passe incorrect");
            User user = userRepository.findByEmail(email).get();
            if (!user.validPassword(password)) throw new IllegalArgumentException("Adresse e-mail ou mot de passe incorrect");
            String role = user.getRole().getName();

            if (role.equals("specialiste")) return specialisteService.findSpecialiste(user.getId());
            return user;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
