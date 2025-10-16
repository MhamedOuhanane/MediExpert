package com.mediexpert.service.impl;

import com.mediexpert.enums.StatusPatient;
import com.mediexpert.model.Record;
import com.mediexpert.repository.interfaces.RecordRepository;
import com.mediexpert.service.interfaces.RecordService;

import java.util.List;
import java.util.UUID;

public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;

    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public Record addRecord(Record record) {
        if (record == null) throw new IllegalArgumentException("Le patient ne peut pas être null.");
        return recordRepository.insertRecord(record);
    }

    @Override
    public Record findRecordById(UUID patientId) {
        if (patientId == null) throw new IllegalArgumentException("L'id de patient ne peut pas être null.");
        return recordRepository.findRecordById(patientId)
                .orElseThrow(() -> new RuntimeException("Aucun patient trouvé avec l'id: " + patientId));
    }

    @Override
    public Record findRecordByCard(String carte) {
        if (carte == null) throw new IllegalArgumentException("L'id de patient ne peut pas être null.");
        try {
            return recordRepository.findRecordByCard(carte)
                    .orElse(null);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public List<Record> getAllRecord() {
        return this.recordRepository.selectRecord().stream()
                .sorted((r1, r2) -> {
                    int o1 = getStatusOrder(r1.getStatus().toString());
                    int o2 = getStatusOrder(r2.getStatus().toString());
                    if (r1 != r2) return Integer.compare(o1, o2);
                    return r2.getUpdatedAt().compareTo(r1.getUpdatedAt());
                })
                .toList();
    }

    @Override
    public Record updateRecord(Record record) {
        if (record == null) throw new IllegalArgumentException("Le patient ne peut pas être null.");
        try {
            Record patient = this.findRecordByCard(record.getCarte());
            patient.setTelephone(record.getTelephone());
            patient.setTension(record.getTension());
            patient.setFrequenceCardiaque(record.getFrequenceCardiaque());
            patient.setTemperature(record.getTemperature());
            patient.setFrequenceRespiratoire(record.getFrequenceRespiratoire());
            patient.setPoids(record.getPoids());
            patient.setTaille(record.getTaille());
            patient.setStatus(StatusPatient.EN_ATTENTE);
            return recordRepository.updateRecord(patient);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Record updateStatus(UUID id, StatusPatient status) {
        if (id == null) throw new IllegalArgumentException("L'id de patient ne peut pas être null.");
        if (status == null) throw new IllegalArgumentException("La nouvelle status de patient ne peut pas être null.");
        try {
            Record patient = this.findRecordById(id);
            if (patient.getStatus() != StatusPatient.EN_ATTENTE && status.equals(StatusPatient.ANNULEE)) throw new RuntimeException("Vous ne devez pas annuler le traitement d’un patient qui suit un consultation.");
            patient.setStatus(status);
            return recordRepository.updateRecord(patient);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Boolean deleteRecord(UUID id) {
        if (id == null) throw new IllegalArgumentException("L'id de patient ne peut pas être null.");
        try {
            Record record = findRecordById(id);
            return recordRepository.deleteRecord(record);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private int getStatusOrder(String status) {
        return switch (status) {
            case "EN_ATTENTE" -> 1;
            case "EN_COURS" -> 2;
            case "TERMINEE" -> 3;
            case "ANNULEE" -> 4;
            default -> 5;
        };
    }
}
