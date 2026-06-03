package com.techplanner.compatibilitylib.utils;

import com.techplanner.compatibilitylib.enums.PcieVersion;
import com.techplanner.compatibilitylib.exceptions.InvalidComponentException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Utility methods shared by the compatibility analyzers.
 */
public final class CompatibilityUtils {

    private CompatibilityUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidComponentException(fieldName + " no puede estar vacío");
        }
        return value.trim();
    }

    public static int requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new InvalidComponentException(fieldName + " debe ser mayor que cero");
        }
        return value;
    }

    public static int requireNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new InvalidComponentException(fieldName + " no puede ser negativo");
        }
        return value;
    }

    public static double requirePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new InvalidComponentException(fieldName + " debe ser mayor que cero");
        }
        return value;
    }

    public static BigDecimal requireNonNegative(BigDecimal value, String fieldName) {
        if (value == null || value.signum() < 0) {
            throw new InvalidComponentException(fieldName + " no puede ser negativo");
        }
        return value;
    }

    public static <T> List<T> immutableList(List<T> values) {
        return values == null ? List.of() : List.copyOf(values);
    }

    public static boolean isPcieBackwardCompatible(PcieVersion gpuVersion, PcieVersion motherboardVersion) {
        return gpuVersion.ordinal() <= motherboardVersion.ordinal();
    }

    public static int applyPercentageMargin(int baseValue, double margin) {
        return (int) Math.ceil(baseValue * (1d + margin));
    }

    public static boolean hasSameOrGreaterGeneration(PcieVersion motherboardVersion, PcieVersion gpuVersion) {
        return Objects.requireNonNull(motherboardVersion, "motherboardVersion").ordinal()
                >= Objects.requireNonNull(gpuVersion, "gpuVersion").ordinal();
    }

    public static String normalizeText(String value) {
        return value == null ? "" : value.trim().toUpperCase().replace(' ', '_');
    }

    public static boolean containsAny(String value, String... candidates) {
        String normalized = normalizeText(value);
        for (String candidate : candidates) {
            if (normalized.contains(normalizeText(candidate))) {
                return true;
            }
        }
        return false;
    }
}