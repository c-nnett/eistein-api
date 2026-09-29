package br.com.fiap.eistein.api.domain.model;

import br.com.fiap.eistein.api.domain.exception.InvalidDomainDataException;

import java.util.List;

public final class DomainValidations {

    private DomainValidations() {
    }

    public static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidDomainDataException("The field %s must be informed".formatted(fieldName));
        }
        return value.trim();
    }

    public static <T> T requireValue(T value, String fieldName) {
        if (value == null) {
            throw new InvalidDomainDataException("The field %s must be informed".formatted(fieldName));
        }
        return value;
    }

    public static List<String> immutableTextList(List<String> values) {
        if (values == null) {
            return List.of();
        }
        return values.stream()
                .filter(value -> value != null && !value.isBlank())
                .map(String::trim)
                .toList();
    }
}
