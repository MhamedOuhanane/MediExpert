package com.mediexpert.repository.interfaces;

import com.mediexpert.model.ActesTechniques;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActesTechniquesRepository {
    ActesTechniques insertActesTechniques(ActesTechniques actesTechniques);
    Optional<ActesTechniques> findActesTechniquesById(UUID actesTechniquesId);
    List<ActesTechniques> selectActesTechniques();
    ActesTechniques updateActesTechniques(ActesTechniques actesTechniques);
    Boolean deleteActesTechniques(ActesTechniques actesTechniques);
}
