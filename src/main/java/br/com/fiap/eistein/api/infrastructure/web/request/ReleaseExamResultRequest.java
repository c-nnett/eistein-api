package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.ReleaseExamResultCommand;

import java.util.UUID;

public record ReleaseExamResultRequest(
        String resultDescription,
        String clinicalReportUrl,
        String responsibleIdentification) {

    public ReleaseExamResultCommand toCommand(UUID examIdentifier) {
        return new ReleaseExamResultCommand(
                examIdentifier, resultDescription, clinicalReportUrl, responsibleIdentification);
    }
}
