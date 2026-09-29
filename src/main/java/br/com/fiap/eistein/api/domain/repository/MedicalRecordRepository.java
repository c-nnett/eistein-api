package br.com.fiap.eistein.api.domain.repository;

import br.com.fiap.eistein.api.domain.model.MedicalRecord;

import java.util.Optional;
import java.util.UUID;

public interface MedicalRecordRepository {

    MedicalRecord save(MedicalRecord medicalRecord);

    Optional<MedicalRecord> findByPatientIdentifier(UUID patientIdentifier);
}
