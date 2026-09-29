package br.com.fiap.eistein.api.application.command;

import br.com.fiap.eistein.api.domain.model.ExamPriority;

import java.util.UUID;

public record RequestExamCommand(
        UUID encounterIdentifier,
        UUID requestingProfessionalIdentifier,
        String examType,
        String sigtapCode,
        String clinicalJustification,
        ExamPriority priority) {
}
