package br.com.fiap.eistein.api.infrastructure.web.mapper;

import br.com.fiap.eistein.api.domain.model.HealthcareProfessional;
import br.com.fiap.eistein.api.infrastructure.web.response.HealthcareProfessionalResponse;

public final class HealthcareProfessionalResponseMapper {

    private HealthcareProfessionalResponseMapper() {
    }

    public static HealthcareProfessionalResponse toResponse(HealthcareProfessional healthcareProfessional) {
        return new HealthcareProfessionalResponse(
                healthcareProfessional.identifier(),
                healthcareProfessional.fullName(),
                healthcareProfessional.council(),
                healthcareProfessional.councilRegistrationNumber(),
                healthcareProfessional.specialty(),
                healthcareProfessional.assignedHealthcareFacility(),
                healthcareProfessional.registeredAt());
    }
}
