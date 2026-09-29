package br.com.fiap.eistein.api.application.usecase.encounter;

import br.com.fiap.eistein.api.application.command.RegisterClinicalNoteCommand;
import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.DomainValidations;
import br.com.fiap.eistein.api.domain.model.ClinicalNote;
import br.com.fiap.eistein.api.domain.model.Encounter;
import br.com.fiap.eistein.api.domain.repository.EncounterRepository;
import br.com.fiap.eistein.api.domain.repository.HealthcareProfessionalRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegisterClinicalNoteUseCase {

    private final EncounterRepository encounterRepository;
    private final HealthcareProfessionalRepository healthcareProfessionalRepository;

    public RegisterClinicalNoteUseCase(
            EncounterRepository encounterRepository,
            HealthcareProfessionalRepository healthcareProfessionalRepository) {
        this.encounterRepository = encounterRepository;
        this.healthcareProfessionalRepository = healthcareProfessionalRepository;
    }

    public Encounter execute(RegisterClinicalNoteCommand command) {
        UUID authoringProfessionalIdentifier = DomainValidations.requireValue(
                command.authoringProfessionalIdentifier(), "authoringProfessionalIdentifier");

        Encounter encounter = encounterRepository.findByIdentifier(command.encounterIdentifier())
                .orElseThrow(() -> new ResourceNotFoundException("Encounter", command.encounterIdentifier()));
        if (healthcareProfessionalRepository.findByIdentifier(authoringProfessionalIdentifier).isEmpty()) {
            throw new ResourceNotFoundException("HealthcareProfessional", authoringProfessionalIdentifier);
        }

        ClinicalNote clinicalNote = new ClinicalNote(
                UUID.randomUUID(),
                authoringProfessionalIdentifier,
                command.clinicalEvolution(),
                command.diagnosis(),
                command.treatmentPlan(),
                LocalDateTime.now());

        Encounter updatedEncounter = encounter
                .withAdditionalClinicalNote(clinicalNote)
                .withOutcome(command.outcome());
        return encounterRepository.save(updatedEncounter);
    }
}
