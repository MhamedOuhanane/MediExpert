package com.mediexpert.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "actesTechniques")
public class ActesTechniques {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private Double prix;

    @ManyToMany(mappedBy = "actesTechniques", cascade = CascadeType.ALL)
    private List<Consultation> consultations = new ArrayList<>();

    private LocalDateTime createdAt;

    public ActesTechniques() {}

    public ActesTechniques(UUID id, String nom, Double prix, List<Consultation> consultations, LocalDateTime createdAt) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.consultations = consultations;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
