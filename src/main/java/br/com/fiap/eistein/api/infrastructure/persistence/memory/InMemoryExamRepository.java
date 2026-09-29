package br.com.fiap.eistein.api.infrastructure.persistence.memory;

import br.com.fiap.eistein.api.domain.model.Exam;
import br.com.fiap.eistein.api.domain.model.ExamStatus;
import br.com.fiap.eistein.api.domain.repository.ExamRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryExamRepository implements ExamRepository {

    private final Map<UUID, Exam> examsByIdentifier = new ConcurrentHashMap<>();

    @Override
    public Exam save(Exam exam) {
        examsByIdentifier.put(exam.identifier(), exam);
        return exam;
    }

    @Override
    public Optional<Exam> findByIdentifier(UUID identifier) {
        return Optional.ofNullable(examsByIdentifier.get(identifier));
    }

    @Override
    public List<Exam> findByEncounterIdentifier(UUID encounterIdentifier) {
        return examsByIdentifier.values().stream()
                .filter(exam -> exam.encounterIdentifier().equals(encounterIdentifier))
                .sorted(Comparator.comparing(Exam::requestedAt))
                .toList();
    }

    @Override
    public List<Exam> findByPatientIdentifierAndOptionalStatus(UUID patientIdentifier, ExamStatus status) {
        return examsByIdentifier.values().stream()
                .filter(exam -> exam.patientIdentifier().equals(patientIdentifier))
                .filter(exam -> status == null || exam.currentStatus() == status)
                .sorted(Comparator.comparing(Exam::requestedAt).reversed())
                .toList();
    }
}
