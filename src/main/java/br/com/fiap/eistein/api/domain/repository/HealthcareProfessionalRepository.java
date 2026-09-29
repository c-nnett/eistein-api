package br.com.fiap.eistein.api.domain.repository;

import br.com.fiap.eistein.api.domain.model.HealthcareProfessional;

import java.util.Optional;
import java.util.UUID;

public interface HealthcareProfessionalRepository {

    HealthcareProfessional save(HealthcareProfessional healthcareProfessional);

    Optional<HealthcareProfessional> findByIdentifier(UUID identifier);

    boolean existsByCouncilRegistrationNumber(String councilRegistrationNumber);
}
