package com.mediexpert.repository.interfaces;

import com.mediexpert.model.Generaliste;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GeneralisteRepository {
    Generaliste insertGeneraliste(Generaliste generaliste);
    Optional<Generaliste> findGeneraliste(UUID generaliste_id);
    List<Generaliste> selectGeneraliste();
    Generaliste updateGeneraliste(Generaliste generaliste);
    Boolean deleteGeneraliste(Generaliste generaliste);
}
