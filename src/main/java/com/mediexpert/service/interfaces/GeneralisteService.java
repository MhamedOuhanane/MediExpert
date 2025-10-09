package com.mediexpert.service.interfaces;

import com.mediexpert.model.Generaliste;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GeneralisteService {
    Generaliste addGeneraliste(Generaliste generaliste);
    Generaliste findGeneraliste(UUID specialisteId);
    List<Generaliste> getAllGeneraliste();
    Generaliste updateGeneraliste(Generaliste generaliste);
    Boolean deleteGeneraliste(Integer id);
}
