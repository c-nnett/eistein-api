package br.com.fiap.eistein.api.infrastructure.persistence.memory;

import br.com.fiap.eistein.api.domain.model.Patient;
import br.com.fiap.eistein.api.domain.model.PatientDocument;
import br.com.fiap.eistein.api.domain.repository.PatientRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPatientRepository implements PatientRepository {

    private final Map<UUID, Patient> patientsByIdentifier = new ConcurrentHashMap<>();

    @Override
    public Patient save(Patient patient) {
        patientsByIdentifier.put(patient.identifier(), patient);
        return patient;
    }

    @Override
    public Optional<Patient> findByIdentifier(UUID identifier) {
        return Optional.ofNullable(patientsByIdentifier.get(identifier));
    }

    @Override
    public Optional<Patient> findByDocument(PatientDocument document) {
        return patientsByIdentifier.values().stream()
                .filter(patient -> patient.isIdentifiedBy(document))
                .findFirst();
    }

    @Override
    public boolean existsByDocument(PatientDocument document) {
        return findByDocument(document).isPresent();
    }
}
