package com.mediexpert.repository.interfaces;

import com.mediexpert.model.Indisponible;

import java.util.Optional;
import java.util.UUID;

public interface IndisponibleRepository {
    Indisponible insert(Indisponible indisponible);
    Optional<Indisponible> find(UUID indisponibleID);
    boolean delete(Indisponible indisponible);
}
