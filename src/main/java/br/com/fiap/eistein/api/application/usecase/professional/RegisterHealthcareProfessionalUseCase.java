package br.com.fiap.eistein.api.application.usecase.professional;

import br.com.fiap.eistein.api.application.command.RegisterHealthcareProfessionalCommand;
import br.com.fiap.eistein.api.domain.exception.DuplicatedProfessionalRegistrationException;
import br.com.fiap.eistein.api.domain.model.HealthcareProfessional;
import br.com.fiap.eistein.api.domain.repository.HealthcareProfessionalRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegisterHealthcareProfessionalUseCase {

    private final HealthcareProfessionalRepository healthcareProfessionalRepository;

    public RegisterHealthcareProfessionalUseCase(
            HealthcareProfessionalRepository healthcareProfessionalRepository) {
        this.healthcareProfessionalRepository = healthcareProfessionalRepository;
    }

    public HealthcareProfessional execute(RegisterHealthcareProfessionalCommand command) {
        if (healthcareProfessionalRepository.existsByCouncilRegistrationNumber(command.councilRegistrationNumber())) {
            throw new DuplicatedProfessionalRegistrationException(command.councilRegistrationNumber());
        }
        HealthcareProfessional healthcareProfessional = new HealthcareProfessional(
                UUID.randomUUID(),
                command.fullName(),
                command.council(),
                command.councilRegistrationNumber(),
                command.specialty(),
                command.assignedHealthcareFacility(),
                LocalDateTime.now());
        return healthcareProfessionalRepository.save(healthcareProfessional);
    }
}
