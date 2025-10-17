package com.mediexpert.service.impl;

import com.mediexpert.enums.ConsultationStatut;
import com.mediexpert.enums.DemandeStatut;
import com.mediexpert.model.Consultation;
import com.mediexpert.model.Demande;
import com.mediexpert.model.Notification;
import com.mediexpert.model.Specialiste;
import com.mediexpert.repository.interfaces.DemandeRepository;
import com.mediexpert.service.interfaces.ConsultationService;
import com.mediexpert.service.interfaces.DemandeService;
import com.mediexpert.service.interfaces.NotificationService;

import java.util.List;
import java.util.UUID;

public class DemandeServiceImpl implements DemandeService {
    private final DemandeRepository demandeRepository;
    private final ConsultationService consultationService;
    private final NotificationService notificationService;

    public DemandeServiceImpl(DemandeRepository demandeRepository, ConsultationService consultationService, NotificationService notificationService) {
        this.demandeRepository = demandeRepository;
        this.consultationService = consultationService;
        this.notificationService = notificationService;
    }

    @Override
    public Demande addDemande(Demande demande) {
        if (demande == null) throw new IllegalArgumentException("Le demande ne peut pas être null.");
        if (demande.getConsultation() == null) throw new IllegalArgumentException("Le consultation demander ne peut pas être null.");
        if (demande.getSpecialiste() == null) throw new IllegalArgumentException("Le specialiste a demander ne peut pas être null.");
        try {
            Demande demande1 = demandeRepository.insert(demande);
            if (demande1 != null) {
                Notification notification = new Notification();
                notification.setDemande(demande1);
                notification.setRead(false);
                notification.setMessage("Vous avais un nouveau demande a la date: " +
                        demande1.getStartDate().toString() + "pour le patient " +
                        demande1.getConsultation().getRecord().getNom() + " " +
                        demande1.getConsultation().getRecord().getPrenom());
                notification = notificationService.addNotification(notification);
            }
            return demande1;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
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
                    if (o1 != o2) return Integer.compare(o1, o2);
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

            if (demande.getConsultation() != null && statut == DemandeStatut.TERMINEE) {
                Consultation consultation = demande.getConsultation();
                consultation.setStatut(ConsultationStatut.TERMINEE);
                consultationService.updateConsultation(consultation);
                demande = findDemandeById(uuid);
            }

            return this.demandeRepository.update(demande);
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
