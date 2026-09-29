package br.com.fiap.eistein.api.domain.repository;

import br.com.fiap.eistein.api.domain.model.Patient;
import br.com.fiap.eistein.api.domain.model.PatientDocument;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository {

    Patient save(Patient patient);

    Optional<Patient> findByIdentifier(UUID identifier);

    Optional<Patient> findByDocument(PatientDocument document);

    boolean existsByDocument(PatientDocument document);
}
