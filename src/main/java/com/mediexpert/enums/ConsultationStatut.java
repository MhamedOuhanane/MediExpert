package com.mediexpert.enums;

public enum ConsultationStatut {
    EN_ATTENTE_AVIS_SPECIALISTE("En attente de l'avis du spécialiste"),
    TERMINEE("Consultation terminée");

    private final String description;

    ConsultationStatut(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
