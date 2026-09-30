package br.com.fiap.eistein.api.infrastructure.web.response;

import br.com.fiap.eistein.api.domain.model.ProfessionalCouncil;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Profissional de saúde cadastrado")
public record HealthcareProfessionalResponse(
        @Schema(description = "Identificador do profissional")
        UUID identifier,
        @Schema(description = "Nome completo")
        String fullName,
        @Schema(description = "Conselho de classe")
        ProfessionalCouncil council,
        @Schema(description = "Número de registro no conselho")
        String councilRegistrationNumber,
        @Schema(description = "Especialidade")
        String specialty,
        @Schema(description = "Unidade de saúde de lotação")
        String assignedHealthcareFacility,
        @Schema(description = "Data e hora do cadastro")
        LocalDateTime registeredAt) {
}
