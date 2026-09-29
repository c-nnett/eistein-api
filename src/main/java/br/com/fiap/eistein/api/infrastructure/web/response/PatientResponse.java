package br.com.fiap.eistein.api.infrastructure.web.response;

import br.com.fiap.eistein.api.domain.model.BiologicalSex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PatientResponse(
        UUID identifier,
        String fullName,
        String taxpayerIdentifier,
        String nationalHealthCardNumber,
        LocalDate birthDate,
        BiologicalSex biologicalSex,
        ContactInformationResponse contactInformation,
        PersistentHealthConditionsResponse persistentHealthConditions,
        LocalDateTime registeredAt) {
}
