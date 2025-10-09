package com.mediexpert.service.impl;

import com.mediexpert.repository.interfaces.RecordRepository;
import com.mediexpert.service.interfaces.RecordService;

public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;

    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }
}
