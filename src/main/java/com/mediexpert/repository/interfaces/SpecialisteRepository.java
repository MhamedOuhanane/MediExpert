package com.mediexpert.repository.interfaces;

import com.mediexpert.model.Specialiste;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpecialisteRepository {
    Specialiste insertSpecialiste(Specialiste specialiste);
    Optional<Specialiste> findSpecialiste(UUID specialisteId);
    List<Specialiste> selectSpecialiste();
    Specialiste updateSpecialiste(Specialiste specialiste);
    Boolean deleteSpecialiste(Specialiste specialiste);
}
