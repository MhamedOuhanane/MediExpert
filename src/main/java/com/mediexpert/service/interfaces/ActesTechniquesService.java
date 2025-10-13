package com.mediexpert.service.interfaces;



import com.mediexpert.model.ActesTechniques;

import java.util.List;
import java.util.UUID;

public interface ActesTechniquesService {
    ActesTechniques addActesTechniques(ActesTechniques actesTechniques);
    ActesTechniques findActesTechniquesById(UUID actesTechniquesId);
    List<ActesTechniques> getAllActesTechniques();
    ActesTechniques updateActesTechniques(ActesTechniques actesTechniques);
    Boolean deleteActesTechniques(UUID id);
}
