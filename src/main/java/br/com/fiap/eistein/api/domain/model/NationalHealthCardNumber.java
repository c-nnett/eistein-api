package br.com.fiap.eistein.api.domain.model;

import br.com.fiap.eistein.api.domain.exception.InvalidDomainDataException;

public record NationalHealthCardNumber(String value) implements PatientDocument {

    static final int REQUIRED_LENGTH = 15;

    private static final int SOCIAL_SECURITY_BASE_LENGTH = 11;
    private static final String DEFINITIVE_NUMBER_PREFIXES = "12";
    private static final String PROVISIONAL_NUMBER_PREFIXES = "789";

    public NationalHealthCardNumber {
        value = PatientDocument.onlyDigits(value);
        if (!isValid(value)) {
            throw new InvalidDomainDataException("The national health card number %s is not valid".formatted(value));
        }
    }

    public static NationalHealthCardNumber of(String rawValue) {
        return new NationalHealthCardNumber(rawValue);
    }

    @Override
    public PatientDocumentType type() {
        return PatientDocumentType.NATIONAL_HEALTH_CARD_NUMBER;
    }

    private static boolean isValid(String digits) {
        if (digits.length() != REQUIRED_LENGTH) {
            return false;
        }
        char prefix = digits.charAt(0);
        if (DEFINITIVE_NUMBER_PREFIXES.indexOf(prefix) >= 0) {
            return isValidDefinitiveNumber(digits);
        }
        if (PROVISIONAL_NUMBER_PREFIXES.indexOf(prefix) >= 0) {
            return isValidProvisionalNumber(digits);
        }
        return false;
    }

    private static boolean isValidDefinitiveNumber(String digits) {
        String socialSecurityBase = digits.substring(0, SOCIAL_SECURITY_BASE_LENGTH);
        int weightedSum = calculateWeightedSum(socialSecurityBase);
        int checkDigit = 11 - (weightedSum % 11);
        if (checkDigit == 11) {
            checkDigit = 0;
        }
        if (checkDigit == 10) {
            weightedSum += 2;
            checkDigit = 11 - (weightedSum % 11);
            return digits.equals(socialSecurityBase + "001" + checkDigit);
        }
        return digits.equals(socialSecurityBase + "000" + checkDigit);
    }

    private static boolean isValidProvisionalNumber(String digits) {
        return calculateWeightedSum(digits) % 11 == 0;
    }

    private static int calculateWeightedSum(String digits) {
        int weightedSum = 0;
        for (int position = 0; position < digits.length(); position++) {
            weightedSum += (digits.charAt(position) - '0') * (REQUIRED_LENGTH - position);
        }
        return weightedSum;
    }
}
