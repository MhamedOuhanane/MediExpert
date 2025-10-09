package com.mediexpert.service.interfaces;

import com.mediexpert.model.Specialiste;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpecialisteService {
    Specialiste addSpecialiste(Specialiste specialiste);
    Specialiste findSpecialiste(UUID specialisteId);
    List<Specialiste> getAllSpecialiste();
    Specialiste updateSpecialiste(Specialiste specialiste);
    Boolean deleteSpecialiste(Integer id);
}
