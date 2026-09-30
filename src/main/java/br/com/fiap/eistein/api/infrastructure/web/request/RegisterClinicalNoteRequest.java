package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.RegisterClinicalNoteCommand;
import br.com.fiap.eistein.api.domain.model.EncounterOutcome;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados para registro de evolução clínica")
public record RegisterClinicalNoteRequest(
        @Schema(description = "Identificador do profissional que registra a evolução", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID authoringProfessionalIdentifier,
        @Schema(description = "Descrição da evolução clínica", example = "Paciente refere dor toracica ha 2 horas, em aperto.", requiredMode = Schema.RequiredMode.REQUIRED)
        String clinicalEvolution,
        @Schema(description = "Hipótese diagnóstica ou diagnóstico", example = "Angina instavel a esclarecer")
        String diagnosis,
        @Schema(description = "Conduta / plano terapêutico", example = "Solicitar troponina e eletrocardiograma.")
        String treatmentPlan,
        @Schema(description = "Desfecho do atendimento; quando omitido, o desfecho atual é mantido", example = "UNDER_OBSERVATION")
        EncounterOutcome outcome) {

    public RegisterClinicalNoteCommand toCommand(UUID encounterIdentifier) {
        return new RegisterClinicalNoteCommand(
                encounterIdentifier,
                authoringProfessionalIdentifier,
                clinicalEvolution,
                diagnosis,
                treatmentPlan,
                outcome);
    }
}
