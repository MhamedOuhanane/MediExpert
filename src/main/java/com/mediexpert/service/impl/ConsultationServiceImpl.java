package com.mediexpert.service.impl;

import com.mediexpert.repository.interfaces.ConsultationRepository;
import com.mediexpert.service.interfaces.ConsultationService;

public class ConsultationServiceImpl implements ConsultationService {
    private final ConsultationRepository consultationRepository;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }
}
