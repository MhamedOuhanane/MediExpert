package com.mediexpert.service.impl;

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
                .sorted((r1, r2) -> r1.getUpdatedAt().compareTo(r2.getUpdatedAt()))
                .toList();
    }

    @Override
    public Record updateRecord(Record record) {
        if (record == null) throw new IllegalArgumentException("Le patient ne peut pas être null.");
        return recordRepository.insertRecord(record);
    }

    @Override
    public Boolean deleteRecord(Integer id) {
        return null;
    }
}
