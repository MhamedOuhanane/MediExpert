package com.mediexpert.repository.interfaces;

import com.mediexpert.model.Demande;
import com.mediexpert.model.Specialiste;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DemandeRepository {
    Demande insert(Demande demande);
    Optional<Demande> find(UUID demandeId);
    Demande update(Demande demande);
    List<Demande> selectSpecialistDemand(Specialiste specialiste);
}
