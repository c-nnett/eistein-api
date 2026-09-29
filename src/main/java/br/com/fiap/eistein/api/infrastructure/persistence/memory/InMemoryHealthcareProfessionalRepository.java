package br.com.fiap.eistein.api.infrastructure.persistence.memory;

import br.com.fiap.eistein.api.domain.model.HealthcareProfessional;
import br.com.fiap.eistein.api.domain.repository.HealthcareProfessionalRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryHealthcareProfessionalRepository implements HealthcareProfessionalRepository {

    private final Map<UUID, HealthcareProfessional> professionalsByIdentifier = new ConcurrentHashMap<>();

    @Override
    public HealthcareProfessional save(HealthcareProfessional healthcareProfessional) {
        professionalsByIdentifier.put(healthcareProfessional.identifier(), healthcareProfessional);
        return healthcareProfessional;
    }

    @Override
    public Optional<HealthcareProfessional> findByIdentifier(UUID identifier) {
        return Optional.ofNullable(professionalsByIdentifier.get(identifier));
    }

    @Override
    public boolean existsByCouncilRegistrationNumber(String councilRegistrationNumber) {
        if (councilRegistrationNumber == null) {
            return false;
        }
        String normalizedRegistration = councilRegistrationNumber.trim();
        return professionalsByIdentifier.values().stream()
                .anyMatch(professional ->
                        professional.councilRegistrationNumber().equalsIgnoreCase(normalizedRegistration));
    }
}
