package br.com.fiap.eistein.api.application.command;

import br.com.fiap.eistein.api.domain.model.EncounterOutcome;

import java.util.UUID;

public record RegisterClinicalNoteCommand(
        UUID encounterIdentifier,
        UUID authoringProfessionalIdentifier,
        String clinicalEvolution,
        String diagnosis,
        String treatmentPlan,
        EncounterOutcome outcome) {
}
