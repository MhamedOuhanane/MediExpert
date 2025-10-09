package com.mediexpert.repository.interfaces;

import com.mediexpert.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {
    Role insertRole(Role role);
    Optional<Role> findRoleById(UUID roleId);
    Optional<Role> findRoleByName(String name);
    List<Role> selectRole();
    Role updateRole(Role role);
    Boolean deleteRole(Role role);
}
