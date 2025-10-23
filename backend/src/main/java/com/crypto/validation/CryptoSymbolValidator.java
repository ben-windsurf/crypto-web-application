package com.crypto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CryptoSymbolValidator implements ConstraintValidator<ValidCryptoSymbol, String> {

    private static final Set<String> VALID_SYMBOLS = new HashSet<>(Arrays.asList(
        "bitcoin", "ethereum", "cardano", "solana", "btc", "eth", "ada", "sol"
    ));

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return VALID_SYMBOLS.contains(value.toLowerCase());
    }
}
