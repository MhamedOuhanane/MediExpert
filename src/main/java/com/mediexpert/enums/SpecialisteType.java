package com.mediexpert.enums;

public enum     SpecialisteType {
    CARDIOLOGIE("Cardiologie: Maladies du cœur et des vaisseaux sanguins (hypertension, insuffisance cardiaque, arythmie, infarctus)"),
    PNEUMOLOGIE("Pneumologie: Maladies respiratoires et pulmonaires"),
    NEUROLOGIE("Neurologie: Troubles du système nerveux"),
    GASTRO_ENTEROLOGIE("Gastro-entérologie: Maladies du système digestif"),
    ENDOCRINOLOGIE("Endocrinologie: Troubles hormonaux et métaboliques (diabète, problèmes thyroïdiens, obésité)"),
    DERMATOLOGIE("Dermatologie: Maladies de la peau"),
    RHUMATOLOGIE("Rhumatologie: Maladies des articulations, os et muscles"),
    PSYCHIATRIE("Psychiatrie: Troubles mentaux et psychologiques (dépression, anxiété, addictions)"),
    NEPHROLOGIE("Néphrologie: Maladies des reins et du système urinaire"),
    ORTHOPEDIE("Orthopédie: Troubles de l’appareil locomoteur (os, muscles, articulations)");

    private final String description;

    SpecialisteType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
