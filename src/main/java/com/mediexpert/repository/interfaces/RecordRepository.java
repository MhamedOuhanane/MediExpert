package com.mediexpert.repository.interfaces;


import com.mediexpert.model.Record;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecordRepository {
    Record insertRecord(Record record);
    Optional<Record> findRecordById(UUID recordId);
    Optional<Record> findRecordByCard(String recordCardCode);
    List<Record> selectRecord();
    Record updateRecord(Record record);
    Boolean deleteRecord(Record record);
}
