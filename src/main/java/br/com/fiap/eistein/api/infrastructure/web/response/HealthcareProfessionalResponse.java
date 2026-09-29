package br.com.fiap.eistein.api.infrastructure.web.response;

import br.com.fiap.eistein.api.domain.model.ProfessionalCouncil;

import java.time.LocalDateTime;
import java.util.UUID;

public record HealthcareProfessionalResponse(
        UUID identifier,
        String fullName,
        ProfessionalCouncil council,
        String councilRegistrationNumber,
        String specialty,
        String assignedHealthcareFacility,
        LocalDateTime registeredAt) {
}
