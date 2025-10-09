package com.mediexpert.service.impl;

import com.mediexpert.model.Generaliste;
import com.mediexpert.repository.interfaces.GeneralisteRepository;
import com.mediexpert.service.interfaces.GeneralisteService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GeneralisteServiceImpl implements GeneralisteService {
    private final GeneralisteRepository generalisteRepository;

    public GeneralisteServiceImpl(GeneralisteRepository generalisteRepository) {
        this.generalisteRepository = generalisteRepository;
    }

    @Override
    public Generaliste addGeneraliste(Generaliste generaliste) {
        if (generaliste == null) throw new IllegalArgumentException("Le generaliste ne peut pas être null.");
        return generalisteRepository.insertGeneraliste(generaliste);
    }

    @Override
    public Generaliste findGeneraliste(UUID generalisteId) {
        if (generalisteId == null) throw new IllegalArgumentException("L'id de generaliste ne peut pas être null.");
        return generalisteRepository.findGeneraliste(generalisteId)
                .orElseThrow(() -> new RuntimeException("Aucun generaliste trouvé avec l'id: " + generalisteId));
    }

    @Override
    public List<Generaliste> getAllGeneraliste() {
        return List.of();
    }

    @Override
    public Generaliste updateGeneraliste(Generaliste generaliste) {
        return null;
    }

    @Override
    public Boolean deleteGeneraliste(Integer id) {
        return null;
    }
}
