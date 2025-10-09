package com.mediexpert.repository.interfaces;

import com.mediexpert.model.Infirmier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InfirmierRepository {
    Infirmier insertInfirmier(Infirmier infirmier);
    Optional<Infirmier> findInfirmier(UUID infirmierId);
    List<Infirmier> selectInfirmier();
    Infirmier updateInfirmier(Infirmier infirmier);
    Boolean deleteInfirmier(Infirmier infirmier);
}
