package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.RegisterHealthcareProfessionalCommand;
import br.com.fiap.eistein.api.domain.model.ProfessionalCouncil;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para cadastro de profissional de saúde")
public record RegisterHealthcareProfessionalRequest(
        @Schema(description = "Nome completo", example = "Ana Ribeiro", requiredMode = Schema.RequiredMode.REQUIRED)
        String fullName,
        @Schema(description = "Conselho de classe", example = "CRM", requiredMode = Schema.RequiredMode.REQUIRED)
        ProfessionalCouncil council,
        @Schema(description = "Número de registro no conselho", example = "123456-SP", requiredMode = Schema.RequiredMode.REQUIRED)
        String councilRegistrationNumber,
        @Schema(description = "Especialidade", example = "Clinica Medica")
        String specialty,
        @Schema(description = "Unidade de saúde de lotação", example = "UBS Vila Nova", requiredMode = Schema.RequiredMode.REQUIRED)
        String assignedHealthcareFacility) {

    public RegisterHealthcareProfessionalCommand toCommand() {
        return new RegisterHealthcareProfessionalCommand(
                fullName, council, councilRegistrationNumber, specialty, assignedHealthcareFacility);
    }
}
