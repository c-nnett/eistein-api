package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.RequestExamCommand;
import br.com.fiap.eistein.api.domain.model.ExamPriority;

import java.util.UUID;

public record RequestExamRequest(
        UUID requestingProfessionalIdentifier,
        String examType,
        String sigtapCode,
        String clinicalJustification,
        ExamPriority priority) {

    public RequestExamCommand toCommand(UUID encounterIdentifier) {
        return new RequestExamCommand(
                encounterIdentifier,
                requestingProfessionalIdentifier,
                examType,
                sigtapCode,
                clinicalJustification,
                priority);
    }
}
