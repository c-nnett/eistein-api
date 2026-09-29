package br.com.fiap.eistein.api.application.usecase.encounter;

import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.Encounter;
import br.com.fiap.eistein.api.domain.repository.EncounterRepository;

import java.util.UUID;

public class FindEncounterByIdentifierUseCase {

    private final EncounterRepository encounterRepository;

    public FindEncounterByIdentifierUseCase(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    public Encounter execute(UUID encounterIdentifier) {
        return encounterRepository.findByIdentifier(encounterIdentifier)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter", encounterIdentifier));
    }
}
