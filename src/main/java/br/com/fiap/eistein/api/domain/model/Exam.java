package br.com.fiap.eistein.api.domain.model;

import br.com.fiap.eistein.api.domain.exception.IllegalExamStatusTransitionException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class Exam {

    private final UUID identifier;
    private final String protocolNumber;
    private final UUID encounterIdentifier;
    private final UUID patientIdentifier;
    private final UUID requestingProfessionalIdentifier;
    private final String examType;
    private final String sigtapCode;
    private final String clinicalJustification;
    private final ExamPriority priority;
    private final LocalDateTime requestedAt;
    private final List<ExamStatusHistoryEntry> statusHistory;

    private ExamStatus currentStatus;
    private String executingHealthcareFacility;
    private LocalDateTime scheduledFor;
    private LocalDateTime collectedAt;
    private LocalDate estimatedResultDate;
    private ExamResult result;

    private Exam(
            UUID identifier,
            String protocolNumber,
            UUID encounterIdentifier,
            UUID patientIdentifier,
            UUID requestingProfessionalIdentifier,
            String examType,
            String sigtapCode,
            String clinicalJustification,
            ExamPriority priority,
            LocalDateTime requestedAt) {
        this.identifier = DomainValidations.requireValue(identifier, "identifier");
        this.protocolNumber = DomainValidations.requireText(protocolNumber, "protocolNumber");
        this.encounterIdentifier = DomainValidations.requireValue(encounterIdentifier, "encounterIdentifier");
        this.patientIdentifier = DomainValidations.requireValue(patientIdentifier, "patientIdentifier");
        this.requestingProfessionalIdentifier = DomainValidations.requireValue(
                requestingProfessionalIdentifier, "requestingProfessionalIdentifier");
        this.examType = DomainValidations.requireText(examType, "examType");
        this.sigtapCode = sigtapCode;
        this.clinicalJustification = DomainValidations.requireText(clinicalJustification, "clinicalJustification");
        this.priority = priority == null ? ExamPriority.ROUTINE : priority;
        this.requestedAt = DomainValidations.requireValue(requestedAt, "requestedAt");
        this.currentStatus = ExamStatus.REQUESTED;
        this.statusHistory = new ArrayList<>();
    }

    public static Exam request(
            String protocolNumber,
            UUID encounterIdentifier,
            UUID patientIdentifier,
            UUID requestingProfessionalIdentifier,
            String examType,
            String sigtapCode,
            String clinicalJustification,
            ExamPriority priority,
            String requestingProfessionalIdentification,
            LocalDateTime requestedAt) {
        Exam exam = new Exam(
                UUID.randomUUID(),
                protocolNumber,
                encounterIdentifier,
                patientIdentifier,
                requestingProfessionalIdentifier,
                examType,
                sigtapCode,
                clinicalJustification,
                priority,
                requestedAt);
        exam.statusHistory.add(ExamStatusHistoryEntry.of(
                null, ExamStatus.REQUESTED, requestedAt, requestingProfessionalIdentification));
        return exam;
    }

    public void applyStatusTransition(ExamStatusTransition transition) {
        ensureTransitionIsAllowed(transition.targetStatus());
        ExamStatus previousStatus = currentStatus;
        currentStatus = transition.targetStatus();
        updateExecutionDetails(transition);
        statusHistory.add(ExamStatusHistoryEntry.of(
                previousStatus,
                currentStatus,
                transition.occurredAt(),
                transition.responsibleIdentification()));
    }

    public void releaseResult(
            String resultDescription,
            String clinicalReportUrl,
            String responsibleIdentification,
            LocalDateTime releaseInstant) {
        ensureTransitionIsAllowed(ExamStatus.RESULT_AVAILABLE);
        ExamStatus previousStatus = currentStatus;
        result = new ExamResult(resultDescription, clinicalReportUrl, releaseInstant);
        currentStatus = ExamStatus.RESULT_AVAILABLE;
        statusHistory.add(ExamStatusHistoryEntry.of(
                previousStatus, currentStatus, releaseInstant, responsibleIdentification));
    }

    private void ensureTransitionIsAllowed(ExamStatus targetStatus) {
        if (!currentStatus.canTransitionTo(targetStatus)) {
            throw new IllegalExamStatusTransitionException(currentStatus, targetStatus);
        }
    }

    private void updateExecutionDetails(ExamStatusTransition transition) {
        if (transition.executingHealthcareFacility() != null) {
            executingHealthcareFacility = transition.executingHealthcareFacility();
        }
        if (transition.scheduledFor() != null) {
            scheduledFor = transition.scheduledFor();
        }
        if (transition.estimatedResultDate() != null) {
            estimatedResultDate = transition.estimatedResultDate();
        }
        if (currentStatus == ExamStatus.COLLECTED) {
            collectedAt = transition.collectedAt() == null ? transition.occurredAt() : transition.collectedAt();
        }
    }

    public UUID identifier() {
        return identifier;
    }

    public String protocolNumber() {
        return protocolNumber;
    }

    public UUID encounterIdentifier() {
        return encounterIdentifier;
    }

    public UUID patientIdentifier() {
        return patientIdentifier;
    }

    public UUID requestingProfessionalIdentifier() {
        return requestingProfessionalIdentifier;
    }

    public String examType() {
        return examType;
    }

    public String sigtapCode() {
        return sigtapCode;
    }

    public String clinicalJustification() {
        return clinicalJustification;
    }

    public ExamPriority priority() {
        return priority;
    }

    public LocalDateTime requestedAt() {
        return requestedAt;
    }

    public ExamStatus currentStatus() {
        return currentStatus;
    }

    public String executingHealthcareFacility() {
        return executingHealthcareFacility;
    }

    public LocalDateTime scheduledFor() {
        return scheduledFor;
    }

    public LocalDateTime collectedAt() {
        return collectedAt;
    }

    public LocalDate estimatedResultDate() {
        return estimatedResultDate;
    }

    public Optional<ExamResult> optionalResult() {
        return Optional.ofNullable(result);
    }

    public List<ExamStatusHistoryEntry> statusHistory() {
        return Collections.unmodifiableList(statusHistory);
    }

    public boolean isConcluded() {
        return currentStatus.isFinal();
    }
}
