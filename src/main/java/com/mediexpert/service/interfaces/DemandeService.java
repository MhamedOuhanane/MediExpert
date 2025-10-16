package com.mediexpert.service.interfaces;

import com.mediexpert.model.Demande;
import com.mediexpert.model.Specialiste;

import java.util.List;
import java.util.UUID;

public interface DemandeService {
    Demande addDemande(Demande demande);
    Demande findDemandeById(UUID demandeId);
    List<Demande> getSpecDemande(Specialiste specialiste);
    Demande updateDemande(Demande demande);
}
