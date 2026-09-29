package br.com.fiap.eistein.api.domain.model;

import br.com.fiap.eistein.api.domain.exception.InvalidDomainDataException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record Patient(
        UUID identifier,
        String fullName,
        TaxpayerIdentifier taxpayerIdentifier,
        NationalHealthCardNumber nationalHealthCardNumber,
        LocalDate birthDate,
        BiologicalSex biologicalSex,
        ContactInformation contactInformation,
        PersistentHealthConditions persistentHealthConditions,
        LocalDateTime registeredAt) {

    public Patient {
        identifier = DomainValidations.requireValue(identifier, "identifier");
        fullName = DomainValidations.requireText(fullName, "fullName");
        taxpayerIdentifier = DomainValidations.requireValue(taxpayerIdentifier, "taxpayerIdentifier");
        birthDate = DomainValidations.requireValue(birthDate, "birthDate");
        biologicalSex = biologicalSex == null ? BiologicalSex.NOT_INFORMED : biologicalSex;
        contactInformation = contactInformation == null ? ContactInformation.empty() : contactInformation;
        persistentHealthConditions = persistentHealthConditions == null
                ? PersistentHealthConditions.none()
                : persistentHealthConditions;
        registeredAt = DomainValidations.requireValue(registeredAt, "registeredAt");
        if (birthDate.isAfter(LocalDate.now())) {
            throw new InvalidDomainDataException("The birth date cannot be in the future");
        }
    }

    public Optional<NationalHealthCardNumber> optionalNationalHealthCardNumber() {
        return Optional.ofNullable(nationalHealthCardNumber);
    }

    public boolean isIdentifiedBy(PatientDocument document) {
        return switch (document) {
            case TaxpayerIdentifier taxpayerDocument -> taxpayerIdentifier.equals(taxpayerDocument);
            case NationalHealthCardNumber healthCardDocument -> healthCardDocument.equals(nationalHealthCardNumber);
        };
    }
}
