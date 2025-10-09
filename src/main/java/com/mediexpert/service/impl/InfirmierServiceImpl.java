package com.mediexpert.service.impl;

import com.mediexpert.model.Infirmier;
import com.mediexpert.repository.interfaces.InfirmierRepository;
import com.mediexpert.service.interfaces.InfirmierService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InfirmierServiceImpl implements InfirmierService {
    private final InfirmierRepository infirmierRepository;

    public InfirmierServiceImpl(InfirmierRepository infirmierRepository) {
        this.infirmierRepository = infirmierRepository;
    }

    @Override
    public Infirmier addInfirmier(Infirmier infirmier) {
        if (infirmier == null) throw new IllegalArgumentException("Le infirmier ne peut pas être null.");
        return infirmierRepository.insertInfirmier(infirmier);
    }

    @Override
    public Infirmier findInfirmier(UUID infirmierId) {
        if (infirmierId == null) throw new IllegalArgumentException("L'id de infirmier ne peut pas être null.");
        return infirmierRepository.findInfirmier(infirmierId)
                .orElseThrow(() -> new RuntimeException("Aucun infirmier trouvé avec l'id: " + infirmierId));
    }

    @Override
    public List<Infirmier> getAllInfirmier() {
        return List.of();
    }

    @Override
    public Infirmier updateInfirmier(Infirmier infirmier) {
        return null;
    }

    @Override
    public Boolean deleteInfirmier(Integer id) {
        return null;
    }
}
