package br.com.fiap.eistein.api.application.command;

import java.util.UUID;

public record ReleaseExamResultCommand(
        UUID examIdentifier,
        String resultDescription,
        String clinicalReportUrl,
        String responsibleIdentification) {
}
