package br.com.fiap.eistein.api.domain.repository;

import br.com.fiap.eistein.api.domain.model.Exam;
import br.com.fiap.eistein.api.domain.model.ExamStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamRepository {

    Exam save(Exam exam);

    Optional<Exam> findByIdentifier(UUID identifier);

    List<Exam> findByEncounterIdentifier(UUID encounterIdentifier);

    List<Exam> findByPatientIdentifierAndOptionalStatus(UUID patientIdentifier, ExamStatus status);
}
