package com.mediexpert.enums;

public enum DemandeStatut {
    EN_ATTENTE_AVIS_SPECIALISTE("Le demande est suspendue en attendant l'avis d'un spécialiste."),
    TERMINEE("Le demande est clôturée après diagnostic ou réception de l'avis du spécialiste."),
    ANNULEE("Le demande est annulée");

    private final String description;

    DemandeStatut(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
