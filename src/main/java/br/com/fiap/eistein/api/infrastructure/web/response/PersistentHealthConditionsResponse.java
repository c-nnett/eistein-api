package br.com.fiap.eistein.api.infrastructure.web.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Condições de saúde permanentes do paciente")
public record PersistentHealthConditionsResponse(
        @Schema(description = "Alergias")
        List<String> allergies,
        @Schema(description = "Comorbidades")
        List<String> comorbidities,
        @Schema(description = "Medicações de uso contínuo")
        List<String> continuousMedications) {
}
