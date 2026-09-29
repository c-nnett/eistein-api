package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.RegisterClinicalNoteCommand;
import br.com.fiap.eistein.api.domain.model.EncounterOutcome;

import java.util.UUID;

public record RegisterClinicalNoteRequest(
        UUID authoringProfessionalIdentifier,
        String clinicalEvolution,
        String diagnosis,
        String treatmentPlan,
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
