package br.com.fiap.eistein.api.domain.repository;

import br.com.fiap.eistein.api.domain.model.Encounter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EncounterRepository {

    Encounter save(Encounter encounter);

    Optional<Encounter> findByIdentifier(UUID identifier);

    List<Encounter> findByMedicalRecordIdentifierOrderedByMostRecent(UUID medicalRecordIdentifier);
}
