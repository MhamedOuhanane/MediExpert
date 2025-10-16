package com.mediexpert.service.impl;

import com.mediexpert.enums.ConsultationStatut;
import com.mediexpert.enums.DemandeStatut;
import com.mediexpert.model.Consultation;
import com.mediexpert.model.Demande;
import com.mediexpert.model.Specialiste;
import com.mediexpert.repository.interfaces.DemandeRepository;
import com.mediexpert.service.interfaces.ConsultationService;
import com.mediexpert.service.interfaces.DemandeService;

import java.util.List;
import java.util.UUID;

public class DemandeServiceImpl implements DemandeService {
    private final DemandeRepository demandeRepository;
    private final ConsultationService consultationService;

    public DemandeServiceImpl(DemandeRepository demandeRepository, ConsultationService consultationService) {
        this.demandeRepository = demandeRepository;
        this.consultationService = consultationService;
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
        if (demande == null) throw new IllegalArgumentException("Le demande ne peut pas être null.");
        return demandeRepository.update(demande);
    }

    @Override
    public Demande responseDemand(UUID uuid, String response, DemandeStatut statut) {
        if (uuid == null) throw new IllegalArgumentException("Le demande ne peut pas être null.");
        if (response == null) throw new IllegalArgumentException("Le response ne peut pas être null.");
        try {
            Demande demande = findDemandeById(uuid);
            demande.setResponse(response);
            demande.setStatut(statut);
            Demande demande1 = this.demandeRepository.update(demande);
            if (demande1 != null && demande1.getConsultation() != null) {
                demande1.getConsultation().setStatut(ConsultationStatut.TERMINEE);
                Consultation consultation = consultationService.updateConsultation(demande1.getConsultation());
            }
            return demande1;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
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
