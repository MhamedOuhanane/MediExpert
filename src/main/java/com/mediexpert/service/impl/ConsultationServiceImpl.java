package com.mediexpert.service.impl;

import com.mediexpert.enums.ConsultationStatut;
import com.mediexpert.enums.StatusPatient;
import com.mediexpert.model.ActesTechniques;
import com.mediexpert.model.Consultation;
import com.mediexpert.model.Record;
import com.mediexpert.repository.interfaces.ConsultationRepository;
import com.mediexpert.service.interfaces.ConsultationService;
import com.mediexpert.service.interfaces.RecordService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConsultationServiceImpl implements ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final RecordService recordService;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository, RecordService recordService) {
        this.consultationRepository = consultationRepository;
        this.recordService = recordService;
    }

    @Override
    public Consultation addConsultation(Consultation consultation) {
        if (consultation == null) throw new IllegalArgumentException("Le consultation ne peut pas être null.");
        try {
            Record patient = recordService.findRecordById(consultation.getRecord().getId());
            if (patient == null || !patient.getStatus().equals(StatusPatient.EN_ATTENTE)) throw new IllegalArgumentException("Une consultation ne peut être effectuée pour un patient qui n'est pas sur la liste d'attente.");
            StatusPatient status = consultation.getStatut().equals(ConsultationStatut.TERMINEE) ? StatusPatient.TERMINEE : StatusPatient.EN_COURS;
            patient.setStatus(status);
            consultation.setRecord(patient);
            return consultationRepository.insertConsultation(consultation);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
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
        if (consultation == null) throw new IllegalArgumentException("Le demande ne peut pas être null.");
        try {
            Consultation consultation1 = this.consultationRepository.updateConsultation(consultation);
            if (consultation1 != null && consultation.getStatut().equals(ConsultationStatut.TERMINEE)) {
                Record record = this.recordService.updateStatus(consultation1.getRecord().getId(), StatusPatient.TERMINEE);
            }
            return consultation1;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Consultation termineConsultation(Consultation consultation) {
        if (consultation == null) throw new IllegalArgumentException("Le demande ne peut pas être null.");
        try {
            Record record = this.recordService.findRecordById(consultation.getRecord().getId());
            if (consultation.getStatut().equals(ConsultationStatut.TERMINEE)) record.setStatus(StatusPatient.TERMINEE);
            consultation.setRecord(record);
            return this.consultationRepository.updateConsultation(consultation);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
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
