package com.mediexpert.service.impl;

import com.mediexpert.model.Consultation;
import com.mediexpert.repository.interfaces.ConsultationRepository;
import com.mediexpert.service.interfaces.ConsultationService;

import java.util.List;
import java.util.UUID;

public class ConsultationServiceImpl implements ConsultationService {
    private final ConsultationRepository consultationRepository;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    @Override
    public Consultation addConsultation(Consultation consultation) {
        if (consultation == null) throw new IllegalArgumentException("Le consultation ne peut pas être null.");
        return consultationRepository.insertConsultation(consultation);
    }

    @Override
    public Consultation findConsultationById(UUID consultationId) {
        if (consultationId == null) throw new IllegalArgumentException("L'id de consultation ne peut pas être null.");
        return consultationRepository.findConsultationById(consultationId)
                .orElseThrow(() -> new RuntimeException("Aucun consultation trouvé avec l'id: " + consultationId));
    }

    @Override
    public List<Consultation> getAllConsultation() {
        return this.consultationRepository.selectConsultation();
    }

    @Override
    public Consultation updateConsultation(Consultation consultation) {
        return null;
    }

    @Override
    public Boolean deleteConsultation(UUID id) {
        if (id == null) throw new IllegalArgumentException("L'id de consultation ne peut pas être null.");
        try {
            Consultation consultation = findConsultationById(id);
            return consultationRepository.deleteConsultation(consultation);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
