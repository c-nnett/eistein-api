package br.com.fiap.eistein.api.infrastructure.web.response;

import br.com.fiap.eistein.api.domain.model.BiologicalSex;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Paciente cadastrado")
public record PatientResponse(
        @Schema(description = "Identificador do paciente")
        UUID identifier,
        @Schema(description = "Nome completo")
        String fullName,
        @Schema(description = "CPF")
        String taxpayerIdentifier,
        @Schema(description = "Cartão Nacional de Saúde (CNS)")
        String nationalHealthCardNumber,
        @Schema(description = "Data de nascimento")
        LocalDate birthDate,
        @Schema(description = "Sexo biológico")
        BiologicalSex biologicalSex,
        @Schema(description = "Dados de contato")
        ContactInformationResponse contactInformation,
        @Schema(description = "Condições de saúde permanentes")
        PersistentHealthConditionsResponse persistentHealthConditions,
        @Schema(description = "Data e hora do cadastro")
        LocalDateTime registeredAt) {
}
