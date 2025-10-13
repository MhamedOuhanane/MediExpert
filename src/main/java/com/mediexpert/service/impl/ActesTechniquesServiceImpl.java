package com.mediexpert.service.impl;

import com.mediexpert.model.ActesTechniques;
import com.mediexpert.repository.interfaces.ActesTechniquesRepository;
import com.mediexpert.service.interfaces.ActesTechniquesService;

import java.util.List;
import java.util.UUID;

public class ActesTechniquesServiceImpl implements ActesTechniquesService {
    private final ActesTechniquesRepository actRepo;

    public ActesTechniquesServiceImpl(ActesTechniquesRepository actRepo) {
        this.actRepo = actRepo;
    }

    @Override
    public ActesTechniques addActesTechniques(ActesTechniques actesTechniques) {
        return null;
    }

    @Override
    public ActesTechniques findActesTechniquesById(UUID uuid) {
        if (uuid == null) throw new IllegalArgumentException("L'id de acte technique ne peut pas être null.");
        return actRepo.findActesTechniquesById(uuid)
                .orElseThrow(() -> new RuntimeException("Aucun acte technique trouvé avec l'id: " + uuid));
    }

    @Override
    public List<ActesTechniques> getAllActesTechniques() {
        return this.actRepo.selectActesTechniques();
    }

    @Override
    public ActesTechniques updateActesTechniques(ActesTechniques actesTechniques) {
        return null;
    }

    @Override
    public Boolean deleteActesTechniques(UUID id) {
        return null;
    }
}
