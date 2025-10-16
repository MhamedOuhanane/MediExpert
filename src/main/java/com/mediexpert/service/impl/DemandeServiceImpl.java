package com.mediexpert.service.impl;

import com.mediexpert.model.Demande;
import com.mediexpert.model.Specialiste;
import com.mediexpert.repository.interfaces.DemandeRepository;
import com.mediexpert.service.interfaces.DemandeService;

import java.util.List;
import java.util.UUID;

public class DemandeServiceImpl implements DemandeService {
    private final DemandeRepository demandeRepository;

    public DemandeServiceImpl(DemandeRepository demandeRepository) {
        this.demandeRepository = demandeRepository;
    }

    @Override
    public Demande addDemande(Demande demande) {
        if (demande == null) throw new IllegalArgumentException("Le demande ne peut pas être null.");
        return demandeRepository.insert(demande);
    }

    @Override
    public Demande findDemandeById(UUID demandeId) {
        if (demandeId == null) throw new IllegalArgumentException("L'id de demande ne peut pas être null.");
        return demandeRepository.find(demandeId)
                .orElseThrow(() -> new RuntimeException("Aucun demande trouvé avec l'id: " + demandeId));
    }

    @Override
    public List<Demande> getSpecDemande(Specialiste specialiste) {
        return this.demandeRepository.selectSpecialistDemand(specialiste).stream()
                .sorted((r1, r2) -> {
                    int o1 = getStatusOrder(r1.getStatut().toString());
                    int o2 = getStatusOrder(r2.getStatut().toString());
                    if (r1 != r2) return Integer.compare(o1, o2);
                    return r2.getUpdatedAt().compareTo(r1.getUpdatedAt());
                })
                .toList();
    }

    @Override
    public Demande updateDemande(Demande demande) {
        if (demande == null) throw new IllegalArgumentException("Le patient ne peut pas être null.");
        return demandeRepository.update(demande);
    }

    private int getStatusOrder(String status) {
        return switch (status) {
            case "EN_ATTENTE_AVIS_SPECIALISTE" -> 1;
            case "TERMINEE" -> 2;
            case "ANNULEE" -> 3;
            default -> 4;
        };
    }
}
