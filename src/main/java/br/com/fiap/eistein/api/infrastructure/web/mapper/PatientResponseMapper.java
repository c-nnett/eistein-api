package br.com.fiap.eistein.api.infrastructure.web.mapper;

import br.com.fiap.eistein.api.domain.model.Patient;
import br.com.fiap.eistein.api.infrastructure.web.response.ContactInformationResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.PatientResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.PersistentHealthConditionsResponse;

public final class PatientResponseMapper {

    private PatientResponseMapper() {
    }

    public static PatientResponse toResponse(Patient patient) {
        return new PatientResponse(
                patient.identifier(),
                patient.fullName(),
                patient.taxpayerIdentifier().value(),
                patient.optionalNationalHealthCardNumber()
                        .map(nationalHealthCardNumber -> nationalHealthCardNumber.value())
                        .orElse(null),
                patient.birthDate(),
                patient.biologicalSex(),
                new ContactInformationResponse(
                        patient.contactInformation().phoneNumber(),
                        patient.contactInformation().emailAddress(),
                        patient.contactInformation().residentialAddress()),
                new PersistentHealthConditionsResponse(
                        patient.persistentHealthConditions().allergies(),
                        patient.persistentHealthConditions().comorbidities(),
                        patient.persistentHealthConditions().continuousMedications()),
                patient.registeredAt());
    }
}
