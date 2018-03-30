package com.appdora.service.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;

    private RandomUtil() {
    }

    /**
     * Generate a password.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Generate an activation key.
     *
     * @return the generated activation key
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generate a reset key.
     *
     * @return the generated reset key
     */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    public static String formatMoedaToBigdecimal(String valor) {
        if (valor != null) {
            String replacePreco = valor;
            replacePreco = replacePreco.replace(".", "");
            replacePreco = replacePreco.replace(",", "");
            return replacePreco.substring(0, replacePreco.length() - 2);
        }
        return "0";
    }
}
