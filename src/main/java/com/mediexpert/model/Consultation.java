package com.mediexpert.model;

import com.mediexpert.enums.ConsultationStatut;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "consultations")
public class Consultation {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String raison;

    @Column(nullable = false)
    private String observations;

    @Column(nullable = false)
    private double prix;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConsultationStatut statut;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    private List<Demande> demandes = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "actes_techniques_consultation",
            joinColumns = @JoinColumn(name = "consultation_id"),
            inverseJoinColumns = @JoinColumn(name = "actes_techniques_id")
    )
    private List<ActesTechniques> actesTechniques = new ArrayList<>();

    public Consultation() {}

    public Consultation(UUID id, String raison, String observations, double prix, ConsultationStatut statut, LocalDateTime createdAt, LocalDateTime updatedAt, Record record, List<Demande> demandes, List<ActesTechniques> actesTechniques) {
        this.id = id;
        this.raison = raison;
        this.observations = observations;
        this.prix = prix;
        this.statut = statut;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.record = record;
        this.demandes = demandes;
        this.actesTechniques = actesTechniques;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public ConsultationStatut getStatut() {
        return statut;
    }

    public void setStatut(ConsultationStatut statut) {
        this.statut = statut;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    public List<ActesTechniques> getActesTechniques() {
        return actesTechniques;
    }

    public void setActesTechniques(List<ActesTechniques> actesTechniques) {
        this.actesTechniques = actesTechniques;
    }
}
