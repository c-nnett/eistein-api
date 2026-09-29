package br.com.fiap.eistein.api.infrastructure.persistence.memory;

import br.com.fiap.eistein.api.domain.model.Encounter;
import br.com.fiap.eistein.api.domain.repository.EncounterRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryEncounterRepository implements EncounterRepository {

    private final Map<UUID, Encounter> encountersByIdentifier = new ConcurrentHashMap<>();

    @Override
    public Encounter save(Encounter encounter) {
        encountersByIdentifier.put(encounter.identifier(), encounter);
        return encounter;
    }

    @Override
    public Optional<Encounter> findByIdentifier(UUID identifier) {
        return Optional.ofNullable(encountersByIdentifier.get(identifier));
    }

    @Override
    public List<Encounter> findByMedicalRecordIdentifierOrderedByMostRecent(UUID medicalRecordIdentifier) {
        return encountersByIdentifier.values().stream()
                .filter(encounter -> encounter.medicalRecordIdentifier().equals(medicalRecordIdentifier))
                .sorted(Comparator.comparing(Encounter::occurredAt).reversed())
                .toList();
    }
}
