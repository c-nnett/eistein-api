package br.com.fiap.eistein.api.application.usecase.patient;

import br.com.fiap.eistein.api.application.command.RegisterPatientCommand;
import br.com.fiap.eistein.api.domain.exception.DuplicatedPatientDocumentException;
import br.com.fiap.eistein.api.domain.model.ContactInformation;
import br.com.fiap.eistein.api.domain.model.NationalHealthCardNumber;
import br.com.fiap.eistein.api.domain.model.Patient;
import br.com.fiap.eistein.api.domain.model.PatientDocument;
import br.com.fiap.eistein.api.domain.model.PersistentHealthConditions;
import br.com.fiap.eistein.api.domain.model.MedicalRecord;
import br.com.fiap.eistein.api.domain.model.TaxpayerIdentifier;
import br.com.fiap.eistein.api.domain.repository.MedicalRecordRepository;
import br.com.fiap.eistein.api.domain.repository.PatientRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegisterPatientUseCase {

    private final PatientRepository patientRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public RegisterPatientUseCase(
            PatientRepository patientRepository, MedicalRecordRepository medicalRecordRepository) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Patient execute(RegisterPatientCommand command) {
        TaxpayerIdentifier taxpayerIdentifier = TaxpayerIdentifier.of(command.taxpayerIdentifier());
        ensureDocumentIsNotAlreadyRegistered(taxpayerIdentifier.value(), taxpayerIdentifier);

        NationalHealthCardNumber nationalHealthCardNumber = resolveNationalHealthCardNumber(command);

        LocalDateTime registrationInstant = LocalDateTime.now();
        Patient patient = new Patient(
                UUID.randomUUID(),
                command.fullName(),
                taxpayerIdentifier,
                nationalHealthCardNumber,
                command.birthDate(),
                command.biologicalSex(),
                new ContactInformation(command.phoneNumber(), command.emailAddress(), command.residentialAddress()),
                new PersistentHealthConditions(
                        command.allergies(), command.comorbidities(), command.continuousMedications()),
                registrationInstant);

        Patient registeredPatient = patientRepository.save(patient);
        medicalRecordRepository.save(MedicalRecord.openFor(registeredPatient.identifier(), registrationInstant));
        return registeredPatient;
    }

    private NationalHealthCardNumber resolveNationalHealthCardNumber(RegisterPatientCommand command) {
        if (command.nationalHealthCardNumber() == null || command.nationalHealthCardNumber().isBlank()) {
            return null;
        }
        NationalHealthCardNumber nationalHealthCardNumber =
                NationalHealthCardNumber.of(command.nationalHealthCardNumber());
        ensureDocumentIsNotAlreadyRegistered(nationalHealthCardNumber.value(), nationalHealthCardNumber);
        return nationalHealthCardNumber;
    }

    private void ensureDocumentIsNotAlreadyRegistered(String documentValue, PatientDocument document) {
        if (patientRepository.existsByDocument(document)) {
            throw new DuplicatedPatientDocumentException(documentValue);
        }
    }
}
