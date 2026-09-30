package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.RegisterPatientCommand;
import br.com.fiap.eistein.api.domain.model.BiologicalSex;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Dados para cadastro de paciente")
public record RegisterPatientRequest(
        @Schema(description = "Nome completo", example = "Joao da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
        String fullName,
        @Schema(description = "CPF, com ou sem pontuação", example = "529.982.247-25", requiredMode = Schema.RequiredMode.REQUIRED)
        String taxpayerIdentifier,
        @Schema(description = "Cartão Nacional de Saúde (CNS), 15 dígitos", example = "894123456789015")
        String nationalHealthCardNumber,
        @Schema(description = "Data de nascimento; não pode estar no futuro", example = "1980-05-12", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDate birthDate,
        @Schema(description = "Sexo biológico; `NOT_INFORMED` quando omitido", example = "MALE")
        BiologicalSex biologicalSex,
        @Schema(description = "Telefone de contato", example = "11999998888")
        String phoneNumber,
        @Schema(description = "E-mail de contato", example = "joao.silva@exemplo.com.br")
        String emailAddress,
        @Schema(description = "Endereço residencial", example = "Rua das Acacias, 120 - Sao Paulo/SP")
        String residentialAddress,
        @Schema(description = "Alergias conhecidas", example = "[\"Dipirona\"]")
        List<String> allergies,
        @Schema(description = "Comorbidades", example = "[\"Hipertensao arterial\"]")
        List<String> comorbidities,
        @Schema(description = "Medicações de uso contínuo", example = "[\"Losartana 50mg\"]")
        List<String> continuousMedications) {

    public RegisterPatientCommand toCommand() {
        return new RegisterPatientCommand(
                fullName,
                taxpayerIdentifier,
                nationalHealthCardNumber,
                birthDate,
                biologicalSex,
                phoneNumber,
                emailAddress,
                residentialAddress,
                allergies,
                comorbidities,
                continuousMedications);
    }
}
