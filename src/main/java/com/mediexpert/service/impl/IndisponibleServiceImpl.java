package com.mediexpert.service.impl;

import com.mediexpert.model.Indisponible;
import com.mediexpert.repository.interfaces.IndisponibleRepository;
import com.mediexpert.service.interfaces.IndisponibleService;

import java.util.UUID;

public class IndisponibleServiceImpl implements IndisponibleService {
    private final IndisponibleRepository indisponibleRepository;

    public IndisponibleServiceImpl(IndisponibleRepository indisponibleRepository) {
        this.indisponibleRepository = indisponibleRepository;
    }

    @Override
    public Indisponible addIndisponible(Indisponible indisponible) {
        if (indisponible == null) throw new IllegalArgumentException("L'indisponibilite ne peut pas être null.");
        return indisponibleRepository.insert(indisponible);
    }

    @Override
    public Indisponible findIndisponible(UUID indisponibleId) {
        if (indisponibleId == null) throw new IllegalArgumentException("L'id d'indisponibilite ne peut pas être null.");
        return indisponibleRepository.find(indisponibleId)
                .orElseThrow(() -> new RuntimeException("Aucun indisponibilite trouvé avec l'id: " + indisponibleId));
    }


    @Override
    public Boolean deleteIndisponible(UUID id) {
        if (id == null) throw new IllegalArgumentException("L'id d'indisponibilite ne peut pas être null.");
        try {
            Indisponible indisponible = findIndisponible(id);
            return indisponibleRepository.delete(indisponible);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
