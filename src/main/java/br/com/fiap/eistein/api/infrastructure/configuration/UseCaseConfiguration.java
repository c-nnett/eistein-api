package br.com.fiap.eistein.api.infrastructure.configuration;

import br.com.fiap.eistein.api.application.usecase.encounter.FindEncounterByIdentifierUseCase;
import br.com.fiap.eistein.api.application.usecase.encounter.OpenEncounterUseCase;
import br.com.fiap.eistein.api.application.usecase.encounter.RegisterClinicalNoteUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.AdvanceExamStatusUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.FindExamByIdentifierUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.ListEncounterExamsUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.ListPatientExamsUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.ReleaseExamResultUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.RequestExamUseCase;
import br.com.fiap.eistein.api.application.usecase.patient.FindPatientByDocumentUseCase;
import br.com.fiap.eistein.api.application.usecase.patient.FindPatientByIdentifierUseCase;
import br.com.fiap.eistein.api.application.usecase.patient.RegisterPatientUseCase;
import br.com.fiap.eistein.api.application.usecase.patient.RetrieveConsolidatedMedicalRecordUseCase;
import br.com.fiap.eistein.api.application.usecase.professional.FindHealthcareProfessionalByIdentifierUseCase;
import br.com.fiap.eistein.api.application.usecase.professional.RegisterHealthcareProfessionalUseCase;
import br.com.fiap.eistein.api.domain.repository.EncounterRepository;
import br.com.fiap.eistein.api.domain.repository.ExamRepository;
import br.com.fiap.eistein.api.domain.repository.HealthcareProfessionalRepository;
import br.com.fiap.eistein.api.domain.repository.MedicalRecordRepository;
import br.com.fiap.eistein.api.domain.repository.PatientRepository;
import br.com.fiap.eistein.api.domain.service.ExamProtocolNumberGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public RegisterPatientUseCase registerPatientUseCase(
            PatientRepository patientRepository, MedicalRecordRepository medicalRecordRepository) {
        return new RegisterPatientUseCase(patientRepository, medicalRecordRepository);
    }

    @Bean
    public FindPatientByDocumentUseCase findPatientByDocumentUseCase(PatientRepository patientRepository) {
        return new FindPatientByDocumentUseCase(patientRepository);
    }

    @Bean
    public FindPatientByIdentifierUseCase findPatientByIdentifierUseCase(PatientRepository patientRepository) {
        return new FindPatientByIdentifierUseCase(patientRepository);
    }

    @Bean
    public RetrieveConsolidatedMedicalRecordUseCase retrieveConsolidatedMedicalRecordUseCase(
            PatientRepository patientRepository,
            MedicalRecordRepository medicalRecordRepository,
            EncounterRepository encounterRepository,
            ExamRepository examRepository) {
        return new RetrieveConsolidatedMedicalRecordUseCase(
                patientRepository, medicalRecordRepository, encounterRepository, examRepository);
    }

    @Bean
    public RegisterHealthcareProfessionalUseCase registerHealthcareProfessionalUseCase(
            HealthcareProfessionalRepository healthcareProfessionalRepository) {
        return new RegisterHealthcareProfessionalUseCase(healthcareProfessionalRepository);
    }

    @Bean
    public FindHealthcareProfessionalByIdentifierUseCase findHealthcareProfessionalByIdentifierUseCase(
            HealthcareProfessionalRepository healthcareProfessionalRepository) {
        return new FindHealthcareProfessionalByIdentifierUseCase(healthcareProfessionalRepository);
    }

    @Bean
    public OpenEncounterUseCase openEncounterUseCase(
            PatientRepository patientRepository,
            MedicalRecordRepository medicalRecordRepository,
            HealthcareProfessionalRepository healthcareProfessionalRepository,
            EncounterRepository encounterRepository) {
        return new OpenEncounterUseCase(
                patientRepository, medicalRecordRepository, healthcareProfessionalRepository, encounterRepository);
    }

    @Bean
    public FindEncounterByIdentifierUseCase findEncounterByIdentifierUseCase(
            EncounterRepository encounterRepository) {
        return new FindEncounterByIdentifierUseCase(encounterRepository);
    }

    @Bean
    public RegisterClinicalNoteUseCase registerClinicalNoteUseCase(
            EncounterRepository encounterRepository,
            HealthcareProfessionalRepository healthcareProfessionalRepository) {
        return new RegisterClinicalNoteUseCase(encounterRepository, healthcareProfessionalRepository);
    }

    @Bean
    public RequestExamUseCase requestExamUseCase(
            EncounterRepository encounterRepository,
            HealthcareProfessionalRepository healthcareProfessionalRepository,
            ExamRepository examRepository,
            ExamProtocolNumberGenerator examProtocolNumberGenerator) {
        return new RequestExamUseCase(
                encounterRepository, healthcareProfessionalRepository, examRepository, examProtocolNumberGenerator);
    }

    @Bean
    public FindExamByIdentifierUseCase findExamByIdentifierUseCase(ExamRepository examRepository) {
        return new FindExamByIdentifierUseCase(examRepository);
    }

    @Bean
    public ListEncounterExamsUseCase listEncounterExamsUseCase(
            EncounterRepository encounterRepository, ExamRepository examRepository) {
        return new ListEncounterExamsUseCase(encounterRepository, examRepository);
    }

    @Bean
    public ListPatientExamsUseCase listPatientExamsUseCase(
            PatientRepository patientRepository, ExamRepository examRepository) {
        return new ListPatientExamsUseCase(patientRepository, examRepository);
    }

    @Bean
    public AdvanceExamStatusUseCase advanceExamStatusUseCase(ExamRepository examRepository) {
        return new AdvanceExamStatusUseCase(examRepository);
    }

    @Bean
    public ReleaseExamResultUseCase releaseExamResultUseCase(ExamRepository examRepository) {
        return new ReleaseExamResultUseCase(examRepository);
    }
}
