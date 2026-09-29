package br.com.fiap.eistein.api.application.usecase.patient;

import br.com.fiap.eistein.api.application.view.ConsolidatedMedicalRecord;
import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.Encounter;
import br.com.fiap.eistein.api.domain.model.Exam;
import br.com.fiap.eistein.api.domain.model.MedicalRecord;
import br.com.fiap.eistein.api.domain.model.Patient;
import br.com.fiap.eistein.api.domain.repository.EncounterRepository;
import br.com.fiap.eistein.api.domain.repository.ExamRepository;
import br.com.fiap.eistein.api.domain.repository.MedicalRecordRepository;
import br.com.fiap.eistein.api.domain.repository.PatientRepository;

import java.util.List;
import java.util.UUID;

public class RetrieveConsolidatedMedicalRecordUseCase {

    private final PatientRepository patientRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final EncounterRepository encounterRepository;
    private final ExamRepository examRepository;

    public RetrieveConsolidatedMedicalRecordUseCase(
            PatientRepository patientRepository,
            MedicalRecordRepository medicalRecordRepository,
            EncounterRepository encounterRepository,
            ExamRepository examRepository) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.encounterRepository = encounterRepository;
        this.examRepository = examRepository;
    }

    public ConsolidatedMedicalRecord execute(UUID patientIdentifier) {
        Patient patient = patientRepository.findByIdentifier(patientIdentifier)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", patientIdentifier));
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatientIdentifier(patientIdentifier)
                .orElseThrow(() -> new ResourceNotFoundException("MedicalRecord", patientIdentifier));

        List<Encounter> recentEncounters =
                encounterRepository.findByMedicalRecordIdentifierOrderedByMostRecent(medicalRecord.identifier());
        List<Exam> ongoingExams = examRepository
                .findByPatientIdentifierAndOptionalStatus(patientIdentifier, null)
                .stream()
                .filter(exam -> !exam.isConcluded())
                .toList();

        return new ConsolidatedMedicalRecord(patient, medicalRecord, recentEncounters, ongoingExams);
    }
}
