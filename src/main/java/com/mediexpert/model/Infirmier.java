package com.mediexpert.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "infermiers")
public class Infirmier extends User {

    public Infirmier() {}

    public Infirmier(UUID id, String nom, String prenom, String email, String password, String carte, LocalDate dateNaissance, String telephone, Role role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, nom, prenom, email, password, carte, dateNaissance, telephone, role, createdAt, updatedAt);
    }
}
