package br.com.fiap.eistein.api.domain.model;

import br.com.fiap.eistein.api.domain.exception.InvalidDomainDataException;

public sealed interface PatientDocument permits TaxpayerIdentifier, NationalHealthCardNumber {

    String value();

    PatientDocumentType type();

    static PatientDocument parse(String rawDocument) {
        String digitsOnly = onlyDigits(rawDocument);
        return switch (digitsOnly.length()) {
            case TaxpayerIdentifier.REQUIRED_LENGTH -> new TaxpayerIdentifier(digitsOnly);
            case NationalHealthCardNumber.REQUIRED_LENGTH -> new NationalHealthCardNumber(digitsOnly);
            default -> throw new InvalidDomainDataException(
                    "The document must contain either %d digits for a taxpayer identifier or %d digits for a national health card number"
                            .formatted(TaxpayerIdentifier.REQUIRED_LENGTH, NationalHealthCardNumber.REQUIRED_LENGTH));
        };
    }

    static String onlyDigits(String rawDocument) {
        if (rawDocument == null || rawDocument.isBlank()) {
            throw new InvalidDomainDataException("The document must be informed");
        }
        return rawDocument.replaceAll("[^0-9]", "");
    }
}
