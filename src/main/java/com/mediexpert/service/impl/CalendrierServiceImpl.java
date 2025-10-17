package com.mediexpert.service.impl;

import com.mediexpert.enums.ConsultationStatut;
import com.mediexpert.enums.StatusPatient;
import com.mediexpert.model.Calendrier;
import com.mediexpert.model.Consultation;
import com.mediexpert.model.Record;
import com.mediexpert.repository.interfaces.CalendrierRepository;
import com.mediexpert.service.interfaces.CalendrierService;

public class CalendrierServiceImpl implements CalendrierService {
    private final CalendrierRepository calendrierRepository;

    public CalendrierServiceImpl(CalendrierRepository calendrierRepository) {
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public Calendrier addCalendrier(Calendrier calendrier) {
        if (calendrier == null) throw new IllegalArgumentException("Les information de jour ajouter ne peut pas être null.");
        if (calendrier.getSpecialiste() == null) throw new IllegalArgumentException("Le specialiste ne peut pas être null.");
        try {
            return calendrierRepository.insert(calendrier);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
