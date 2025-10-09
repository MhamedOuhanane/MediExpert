package com.mediexpert.service.impl;

import com.mediexpert.model.Specialiste;
import com.mediexpert.repository.interfaces.SpecialisteRepository;
import com.mediexpert.service.interfaces.SpecialisteService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpecialisteServiceImpl implements SpecialisteService {
    private final SpecialisteRepository specialisteRepository;

    public SpecialisteServiceImpl(SpecialisteRepository specialisteRepository) {
        this.specialisteRepository = specialisteRepository;
    }

    @Override
    public Specialiste addSpecialiste(Specialiste specialiste) {
        if (specialiste == null) throw new IllegalArgumentException("Le specialiste ne peut pas être null.");
        return specialisteRepository.insertSpecialiste(specialiste);
    }

    @Override
    public Specialiste findSpecialiste(UUID specialisteId) {
        if (specialisteId == null) throw new IllegalArgumentException("L'id de specialiste ne peut pas être null.");
        return specialisteRepository.findSpecialiste(specialisteId)
                .orElseThrow(() -> new RuntimeException("Aucun specialiste trouvé avec l'id: " + specialisteId));
    }

    @Override
    public List<Specialiste> getAllSpecialiste() {
        return List.of();
    }

    @Override
    public Specialiste updateSpecialiste(Specialiste specialiste) {
        return null;
    }

    @Override
    public Boolean deleteSpecialiste(Integer id) {
        return null;
    }
}
