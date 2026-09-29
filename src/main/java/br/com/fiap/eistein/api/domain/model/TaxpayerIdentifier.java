package br.com.fiap.eistein.api.domain.model;

import br.com.fiap.eistein.api.domain.exception.InvalidDomainDataException;

public record TaxpayerIdentifier(String value) implements PatientDocument {

    static final int REQUIRED_LENGTH = 11;

    private static final int FIRST_CHECK_DIGIT_POSITION = 9;
    private static final int SECOND_CHECK_DIGIT_POSITION = 10;

    public TaxpayerIdentifier {
        value = PatientDocument.onlyDigits(value);
        if (!isValid(value)) {
            throw new InvalidDomainDataException("The taxpayer identifier %s is not valid".formatted(value));
        }
    }

    public static TaxpayerIdentifier of(String rawValue) {
        return new TaxpayerIdentifier(rawValue);
    }

    @Override
    public PatientDocumentType type() {
        return PatientDocumentType.TAXPAYER_IDENTIFIER;
    }

    private static boolean isValid(String digits) {
        if (digits.length() != REQUIRED_LENGTH || hasAllDigitsEqual(digits)) {
            return false;
        }
        return digitAt(digits, FIRST_CHECK_DIGIT_POSITION) == calculateCheckDigit(digits, FIRST_CHECK_DIGIT_POSITION)
                && digitAt(digits, SECOND_CHECK_DIGIT_POSITION) == calculateCheckDigit(digits, SECOND_CHECK_DIGIT_POSITION);
    }

    private static boolean hasAllDigitsEqual(String digits) {
        return digits.chars().distinct().count() == 1;
    }

    private static int calculateCheckDigit(String digits, int checkDigitPosition) {
        int weight = checkDigitPosition + 1;
        int weightedSum = 0;
        for (int position = 0; position < checkDigitPosition; position++) {
            weightedSum += digitAt(digits, position) * weight--;
        }
        int remainder = weightedSum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    private static int digitAt(String digits, int position) {
        return digits.charAt(position) - '0';
    }
}
