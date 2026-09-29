package br.com.fiap.eistein.api.application.usecase.professional;

import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.HealthcareProfessional;
import br.com.fiap.eistein.api.domain.repository.HealthcareProfessionalRepository;

import java.util.UUID;

public class FindHealthcareProfessionalByIdentifierUseCase {

    private final HealthcareProfessionalRepository healthcareProfessionalRepository;

    public FindHealthcareProfessionalByIdentifierUseCase(
            HealthcareProfessionalRepository healthcareProfessionalRepository) {
        this.healthcareProfessionalRepository = healthcareProfessionalRepository;
    }

    public HealthcareProfessional execute(UUID professionalIdentifier) {
        return healthcareProfessionalRepository.findByIdentifier(professionalIdentifier)
                .orElseThrow(() -> new ResourceNotFoundException("HealthcareProfessional", professionalIdentifier));
    }
}
