package com.mediexpert.service.interfaces;

import com.mediexpert.enums.StatusPatient;
import com.mediexpert.model.Record;

import java.util.List;
import java.util.UUID;

public interface RecordService {
    Record addRecord(Record record);
    Record findRecordById(UUID recordId);
    Record findRecordByCard(String carte);
    List<Record> getAllRecord();
    Record updateRecord(Record record);
    Record updateStatus(UUID id, StatusPatient status);
    Boolean deleteRecord(UUID id);
}
