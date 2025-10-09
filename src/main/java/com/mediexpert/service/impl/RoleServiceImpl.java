package com.mediexpert.service.impl;

import com.mediexpert.model.Role;
import com.mediexpert.repository.interfaces.RoleRepository;
import com.mediexpert.service.interfaces.RoleService;

import java.util.UUID;

public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addRole(Role role) {
        if (role == null) throw new IllegalArgumentException("Le role ne peut pas être null.");
        return roleRepository.insertRole(role);
    }

    @Override
    public Role findRoleById(UUID uuid) {
        if (uuid == null) throw new IllegalArgumentException("L'id de role ne peut pas être null.");
        return roleRepository.findRoleById(uuid)
                .orElseThrow(() -> new RuntimeException("Aucun role trouvé avec l'id: " + uuid));
    }

    @Override
    public Role findRoleByName(String name) {
        if (name == null) throw new IllegalArgumentException("Le nom de role ne peut pas être null.");
        return roleRepository.findRoleByName(name)
                .orElseThrow(() -> new RuntimeException("Aucun role trouvé avec le nom: " + name));
    }
}
