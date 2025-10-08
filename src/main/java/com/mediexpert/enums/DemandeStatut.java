package com.mediexpert.enums;

public enum DemandeStatut {
    EN_ATTENTE_AVIS_SPECIALISTE("La consultation est suspendue en attendant l'avis d'un spécialiste."),
    TERMINEE("La consultation est clôturée après diagnostic ou réception de l'avis du spécialiste.");

    private final String description;

    DemandeStatut(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
