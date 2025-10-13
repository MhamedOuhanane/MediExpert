package com.mediexpert.repository.interfaces;

import com.mediexpert.model.Consultation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultationRepository {
    Consultation insertConsultation(Consultation consultation);
    Optional<Consultation> findConsultationById(UUID consultationId);
    List<Consultation> selectConsultation();
    Consultation updateConsultation(Consultation consultation);
    Boolean deleteConsultation(Consultation consultation);
}
