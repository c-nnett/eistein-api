package br.com.fiap.eistein.api.infrastructure.persistence.memory;

import br.com.fiap.eistein.api.domain.model.MedicalRecord;
import br.com.fiap.eistein.api.domain.repository.MedicalRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryMedicalRecordRepository implements MedicalRecordRepository {

    private final Map<UUID, MedicalRecord> medicalRecordsByPatientIdentifier = new ConcurrentHashMap<>();

    @Override
    public MedicalRecord save(MedicalRecord medicalRecord) {
        medicalRecordsByPatientIdentifier.put(medicalRecord.patientIdentifier(), medicalRecord);
        return medicalRecord;
    }

    @Override
    public Optional<MedicalRecord> findByPatientIdentifier(UUID patientIdentifier) {
        return Optional.ofNullable(medicalRecordsByPatientIdentifier.get(patientIdentifier));
    }
}
