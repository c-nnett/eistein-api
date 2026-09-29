package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.RegisterHealthcareProfessionalCommand;
import br.com.fiap.eistein.api.domain.model.ProfessionalCouncil;

public record RegisterHealthcareProfessionalRequest(
        String fullName,
        ProfessionalCouncil council,
        String councilRegistrationNumber,
        String specialty,
        String assignedHealthcareFacility) {

    public RegisterHealthcareProfessionalCommand toCommand() {
        return new RegisterHealthcareProfessionalCommand(
                fullName, council, councilRegistrationNumber, specialty, assignedHealthcareFacility);
    }
}
