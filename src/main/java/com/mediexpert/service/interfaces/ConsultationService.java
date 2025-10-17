package com.mediexpert.service.interfaces;

import com.mediexpert.model.Consultation;

import java.util.List;
import java.util.UUID;

public interface ConsultationService {
    Consultation addConsultation(Consultation consultation);
    Consultation findConsultationById(UUID consultationId);
    List<Consultation> getAllConsultation();
    Consultation updateConsultation(Consultation consultation);
    Consultation termineConsultation(Consultation consultation);
    Boolean deleteConsultation(UUID id);

}
