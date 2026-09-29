package br.com.fiap.eistein.api.infrastructure.web.response;

import java.util.List;

public record PersistentHealthConditionsResponse(
        List<String> allergies,
        List<String> comorbidities,
        List<String> continuousMedications) {
}
