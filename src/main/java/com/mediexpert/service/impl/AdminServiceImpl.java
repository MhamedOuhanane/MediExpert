package com.mediexpert.service.impl;

import com.mediexpert.model.Admin;
import com.mediexpert.repository.interfaces.AdminRepository;
import com.mediexpert.service.interfaces.AdminService;

public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin addAdmin(Admin admin) {
        if (admin == null) throw new IllegalArgumentException("Le admin ne peut pas être null.");
        return adminRepository.insertAdmin(admin);
    }
}
