package br.com.fiap.eistein.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record HealthcareProfessional(
        UUID identifier,
        String fullName,
        ProfessionalCouncil council,
        String councilRegistrationNumber,
        String specialty,
        String assignedHealthcareFacility,
        LocalDateTime registeredAt) {

    public HealthcareProfessional {
        identifier = DomainValidations.requireValue(identifier, "identifier");
        fullName = DomainValidations.requireText(fullName, "fullName");
        council = DomainValidations.requireValue(council, "council");
        councilRegistrationNumber = DomainValidations.requireText(councilRegistrationNumber, "councilRegistrationNumber");
        assignedHealthcareFacility = DomainValidations.requireText(assignedHealthcareFacility, "assignedHealthcareFacility");
        registeredAt = DomainValidations.requireValue(registeredAt, "registeredAt");
    }

    public String qualifiedRegistration() {
        return "%s %s".formatted(council, councilRegistrationNumber);
    }
}
