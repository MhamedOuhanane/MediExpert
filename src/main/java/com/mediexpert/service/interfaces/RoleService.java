package com.mediexpert.service.interfaces;

import com.mediexpert.model.Role;

import java.util.UUID;

public interface RoleService {
    Role addRole(Role role);
    Role findRoleById(UUID uuid);
    Role findRoleByName(String name);
}
