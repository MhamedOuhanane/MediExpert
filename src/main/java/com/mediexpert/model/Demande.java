package com.mediexpert.model;

import com.mediexpert.enums.DemandeStatut;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "demandes")
public class Demande {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String question;

    private String response;

    @Column(nullable = false)
    private DemandeStatut statut;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @ManyToOne
    @JoinColumn(name = "specialiste_id")
    private Specialiste specialiste;

    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();

    public Demande(){}

    public Demande(UUID id, String question, String response, DemandeStatut statut, LocalDateTime startDate, LocalDateTime createdAt, LocalDateTime updatedAt, Consultation consultation, Specialiste specialiste, List<Notification> notifications) {
        this.id = id;
        this.question = question;
        this.response = response;
        this.statut = statut;
        this.startDate = startDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.consultation = consultation;
        this.specialiste = specialiste;
        this.notifications = notifications;
    }

    public Demande(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public DemandeStatut getStatut() {
        return statut;
    }

    public void setStatut(DemandeStatut statut) {
        this.statut = statut;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
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

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public Specialiste getSpecialiste() {
        return specialiste;
    }

    public void setSpecialiste(Specialiste specialiste) {
        this.specialiste = specialiste;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
