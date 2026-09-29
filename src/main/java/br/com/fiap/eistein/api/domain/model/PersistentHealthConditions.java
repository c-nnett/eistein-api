package br.com.fiap.eistein.api.domain.model;

import java.util.List;

public record PersistentHealthConditions(
        List<String> allergies,
        List<String> comorbidities,
        List<String> continuousMedications) {

    public PersistentHealthConditions {
        allergies = DomainValidations.immutableTextList(allergies);
        comorbidities = DomainValidations.immutableTextList(comorbidities);
        continuousMedications = DomainValidations.immutableTextList(continuousMedications);
    }

    public static PersistentHealthConditions none() {
        return new PersistentHealthConditions(List.of(), List.of(), List.of());
    }
}
