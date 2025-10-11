package com.mediexpert.model;

import com.mediexpert.enums.StatusPatient;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "records")
public class Record {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private LocalDate dateNaissance;

    @Column(nullable = false, unique = true)
    private String carte;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private Integer tension;

    @Column(nullable = false)
    private Integer frequenceCardiaque;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private Integer frequenceRespiratoire;

    @Column(nullable = false)
    private Double poids;

    @Column(nullable = false)
    private Double taille;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPatient status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<Consultation> consultations = new ArrayList<>();

    public Record() {}

    public Record(UUID id, String nom, String prenom, LocalDate dateNaissance, String carte, String telephone, Integer tension, Integer frequenceCardiaque, Double temperature, Integer frequenceRespiratoire, Double poids, Double taille, StatusPatient status, LocalDateTime createdAt, LocalDateTime updatedAt, List<Consultation> consultations) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.carte = carte;
        this.telephone = telephone;
        this.tension = tension;
        this.frequenceCardiaque = frequenceCardiaque;
        this.temperature = temperature;
        this.frequenceRespiratoire = frequenceRespiratoire;
        this.poids = poids;
        this.taille = taille;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.consultations = consultations;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getCarte() {
        return carte;
    }

    public void setCarte(String carte) {
        this.carte = carte;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getTension() {
        return tension;
    }

    public void setTension(Integer tension) {
        this.tension = tension;
    }

    public Integer getFrequenceCardiaque() {
        return frequenceCardiaque;
    }

    public void setFrequenceCardiaque(Integer frequenceCardiaque) {
        this.frequenceCardiaque = frequenceCardiaque;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getFrequenceRespiratoire() {
        return frequenceRespiratoire;
    }

    public void setFrequenceRespiratoire(Integer frequenceRespiratoire) {
        this.frequenceRespiratoire = frequenceRespiratoire;
    }

    public Double getPoids() {
        return poids;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public Double getTaille() {
        return taille;
    }

    public void setTaille(Double taille) {
        this.taille = taille;
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

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", carte='" + carte + '\'' +
                ", telephone='" + telephone + '\'' +
                ", tension=" + tension +
                ", frequenceCardiaque=" + frequenceCardiaque +
                ", temperature=" + temperature +
                ", frequenceRespiratoire=" + frequenceRespiratoire +
                ", poids=" + poids +
                ", taille=" + taille +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", consultations=" + consultations +
                '}';
    }

    public StatusPatient getStatus() {
        return status;
    }

    public void setStatus(StatusPatient status) {
        this.status = status;
    }
}
