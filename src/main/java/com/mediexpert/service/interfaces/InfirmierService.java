package com.mediexpert.service.interfaces;

import com.mediexpert.model.Infirmier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InfirmierService {
    Infirmier addInfirmier(Infirmier infirmier);
    Infirmier findInfirmier(UUID infirmierId);
    List<Infirmier> getAllInfirmier();
    Infirmier updateInfirmier(Infirmier infirmier);
    Boolean deleteInfirmier(Integer id);
}
