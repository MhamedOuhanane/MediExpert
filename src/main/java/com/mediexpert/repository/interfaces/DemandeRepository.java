package com.mediexpert.repository.interfaces;

import com.mediexpert.model.Demande;

import java.util.Optional;

public interface DemandeRepository {
    Demande insert(Demande demande);
    Optional<Demande> find(Demande demande);
    Demande update(Demande demande);
}
