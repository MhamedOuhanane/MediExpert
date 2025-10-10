package com.mediexpert.service.impl;

import com.mediexpert.model.User;
import com.mediexpert.repository.interfaces.SpecialisteRepository;
import com.mediexpert.repository.interfaces.UserRepository;
import com.mediexpert.service.interfaces.SpecialisteService;
import com.mediexpert.service.interfaces.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SpecialisteRepository specialisteRepository;

    public UserServiceImpl (UserRepository userRepository, SpecialisteRepository specialisteRepository) {
        this.userRepository = userRepository;
        this.specialisteRepository = specialisteRepository;
    }

    @Override
    public Boolean checkEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public Object login(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Adresse e-mail ou mot de passe ne peut pa etre null");
        }
        if (!checkEmail(email)) throw new IllegalArgumentException("Adresse e-mail ou mot de passe incorrect");
        User user = userRepository.findByEmail(email).get();
        if (!user.validPassword(password)) throw new IllegalArgumentException("Adresse e-mail ou mot de passe incorrect");
        String role = user.getRole().getName();

        if (role.equals("specialiste")) return specialisteRepository.findSpecialiste(user.getId());
        return user;
    }
}
