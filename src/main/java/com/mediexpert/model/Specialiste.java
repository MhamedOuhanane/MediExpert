package com.mediexpert.model;

import com.mediexpert.enums.SpecialisteType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "specialistes")
public class Specialiste extends User {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpecialisteType specialisteType;

    @Column(nullable = false)
    private Double tarif;

    @OneToMany(mappedBy = "specialiste", cascade = CascadeType.ALL)
    private List<Demande> demandes = new ArrayList<>();

    @OneToMany(mappedBy = "specialiste", cascade = CascadeType.ALL)
    private List<Calendrier> calendriers = new ArrayList<>();

    public Specialiste() {}

    public Specialiste(UUID id, String nom, String prenom, String email, String password, String carte, LocalDate dateNaissance, String telephone, Role role, LocalDateTime createdAt, LocalDateTime updatedAt, SpecialisteType specialiteType, Double tarif, List<Demande> demandes, List<Calendrier> calendriers) {
        super(id, nom, prenom, email, password, carte, dateNaissance, telephone, role, createdAt, updatedAt);
        this.specialisteType = specialiteType;
        this.tarif = tarif;
        this.demandes = demandes;
        this.calendriers = calendriers;
    }

    public SpecialisteType getSpecialite() {
        return specialisteType;
    }

    public void setSpecialite(SpecialisteType specialite) {
        this.specialisteType = specialite;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    public List<Calendrier> getCalendriers() {
        return calendriers;
    }

    public void setCalendriers(List<Calendrier> calendriers) {
        this.calendriers = calendriers;
    }
}
