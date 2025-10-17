package com.mediexpert.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "calendriers")
public class Calendrier {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private Boolean disponibilite = true;

    @ManyToOne
    @JoinColumn(name = "specialiste_id")
    private Specialiste specialiste;

    @OneToMany(mappedBy = "calendrier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Indisponible> indisponibles = new ArrayList<>();

    public Calendrier() {}

    public Calendrier(UUID id, LocalDate date, LocalTime startTime, LocalTime endTime, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean disponibilite, Specialiste specialiste, List<Indisponible> indisponibles) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.disponibilite = disponibilite;
        this.specialiste = specialiste;
        this.indisponibles = indisponibles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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

    public Boolean getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(Boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public Specialiste getSpecialiste() {
        return specialiste;
    }

    public void setSpecialiste(Specialiste specialiste) {
        this.specialiste = specialiste;
    }

    public List<Indisponible> getIndisponibles() {
        return indisponibles;
    }

    public void setIndisponibles(List<Indisponible> indisponibles) {
        this.indisponibles = indisponibles;
    }
}
