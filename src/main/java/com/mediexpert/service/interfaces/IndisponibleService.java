package com.mediexpert.service.interfaces;

import com.mediexpert.model.Indisponible;

import java.util.List;
import java.util.UUID;

public interface IndisponibleService {
    Indisponible addIndisponible(Indisponible indisponible);
    Indisponible findIndisponible(UUID indisponibleId);
    Boolean deleteIndisponible(UUID id);
}
