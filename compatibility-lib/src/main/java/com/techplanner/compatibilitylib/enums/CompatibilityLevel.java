package com.techplanner.compatibilitylib.enums;

/**
 * Qualitative interpretation of a numeric compatibility score.
 */
public enum CompatibilityLevel {
    EXCELLENT,
    HIGH,
    MEDIUM,
    LOW;

    public static CompatibilityLevel fromScore(int score) {
        if (score >= 95) {
            return EXCELLENT;
        }
        if (score >= 80) {
            return HIGH;
        }
        if (score >= 50) {
            return MEDIUM;
        }
        return LOW;
    }
}