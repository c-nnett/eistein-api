package br.com.fiap.eistein.api.application.command;

import br.com.fiap.eistein.api.domain.model.ProfessionalCouncil;

public record RegisterHealthcareProfessionalCommand(
        String fullName,
        ProfessionalCouncil council,
        String councilRegistrationNumber,
        String specialty,
        String assignedHealthcareFacility) {
}
