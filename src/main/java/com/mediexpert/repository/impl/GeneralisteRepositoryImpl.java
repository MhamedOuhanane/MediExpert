package com.mediexpert.repository.impl;

import com.mediexpert.model.Generaliste;
import com.mediexpert.repository.interfaces.GeneralisteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GeneralisteRepositoryImpl implements GeneralisteRepository {
    @Override
    public Generaliste insertGeneraliste(Generaliste generaliste) {
        return null;
    }

    @Override
    public Optional<Generaliste> findGeneraliste(UUID generaliste_id) {
        return Optional.empty();
    }

    @Override
    public List<Generaliste> selectGeneraliste() {
        return List.of();
    }

    @Override
    public Generaliste updateGeneraliste(Generaliste generaliste) {
        return null;
    }

    @Override
    public Boolean deleteGeneraliste(Generaliste generaliste) {
        return null;
    }
}
