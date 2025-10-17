package com.mediexpert.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mediexpert.enums.DemandeStatut;
import com.mediexpert.model.Calendrier;
import com.mediexpert.model.Demande;
import com.mediexpert.model.Indisponible;
import com.mediexpert.model.Specialiste;
import com.mediexpert.repository.interfaces.SpecialisteRepository;
import com.mediexpert.service.interfaces.SpecialisteService;

import java.time.LocalTime;
import java.util.*;

public class SpecialisteServiceImpl implements SpecialisteService {
    private final SpecialisteRepository specialisteRepository;

    public SpecialisteServiceImpl(SpecialisteRepository specialisteRepository) {
        this.specialisteRepository = specialisteRepository;
    }

    @Override
    public Specialiste addSpecialiste(Specialiste specialiste) {
        if (specialiste == null) throw new IllegalArgumentException("Le specialiste ne peut pas être null.");
        return specialisteRepository.insertSpecialiste(specialiste);
    }

    @Override
    public Specialiste findSpecialiste(UUID specialisteId) {
        if (specialisteId == null) throw new IllegalArgumentException("L'id de specialiste ne peut pas être null.");
        return specialisteRepository.findSpecialiste(specialisteId)
                .orElseThrow(() -> new RuntimeException("Aucun specialiste trouvé avec l'id: " + specialisteId));
    }

    @Override
    public List<Specialiste> getAllSpecialiste() {
        return specialisteRepository.selectSpecialiste();
    }

    @Override
    public Specialiste updateSpecialiste(Specialiste specialiste) {
        return null;
    }

    @Override
    public Boolean deleteSpecialiste(Integer id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> calendrierJson(Specialiste specialiste) throws JsonProcessingException {
        List<Map<String, Object>> calendrierList = new ArrayList<>();
        if (specialiste != null) {
            for (Calendrier cal : specialiste.getCalendriers()) {
                Map<String, Object> calMap = new HashMap<>();
                calMap.put("id", cal.getId());
                calMap.put("date", cal.getDate());
                calMap.put("startTime", cal.getStartTime());
                calMap.put("endTime", cal.getEndTime());
                calMap.put("disponibilite", cal.getDisponibilite());

                List<Map<String, Object>> indisponibles = new ArrayList<>();
                for (Indisponible indi : cal.getIndisponibles()) {
                    Map<String, Object> indiMap = new HashMap<>();
                    indiMap.put("id", indi.getId());
                    indiMap.put("startTime", indi.getStartTime());
                    indiMap.put("endTime", indi.getEndTime());
                    indisponibles.add(indiMap);
                }
                calMap.put("indisponibles", indisponibles);

                List<Map<String, Object>> reserves = new ArrayList<>();
                for (Demande demand : specialiste.getDemandes()) {
                    if (!demand.getStatut().equals(DemandeStatut.ANNULEE) &&
                            demand.getStartDate().toLocalDate().equals(cal.getDate())) {

                        Map<String, Object> reseMap = new HashMap<>();

                        LocalTime startTime = demand.getStartDate().toLocalTime();
                        LocalTime endTime = startTime.plusMinutes(30);

                        reseMap.put("startTime", new int[]{startTime.getHour(), startTime.getMinute()});
                        reseMap.put("endTime", new int[]{endTime.getHour(), endTime.getMinute()});
                        reseMap.put("status", demand.getStatut().toString());

                        reserves.add(reseMap);
                    }
                }
                calMap.put("reserves", reserves);
                calendrierList.add(calMap);
            }
        }

        return calendrierList;
    }
}
