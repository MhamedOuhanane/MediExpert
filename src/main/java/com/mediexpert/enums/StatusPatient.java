package com.mediexpert.enums;

public enum StatusPatient {
    EN_ATTENTE("Le patient attend sa consultation"),
    EN_COURS("Le patient est en cours de consultation"),
    TERMINEE("La consultation du patient est terminée"),
    ANNULEE("La consultation du patient a été annulée");

    private final String description;

    StatusPatient(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
