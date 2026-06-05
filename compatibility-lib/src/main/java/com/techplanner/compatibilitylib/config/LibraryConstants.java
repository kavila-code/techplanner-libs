package com.techplanner.compatibilitylib.config;

/**
 * Shared constants for the compatibility library.
 */
public final class LibraryConstants {

    public static final double POWER_MARGIN_PERCENTAGE = 0.25d;
    public static final int SCORE_MIN = 0;
    public static final int SCORE_MAX = 100;
    public static final int PERFECT_SCORE = 100;
    public static final int COMPATIBLE_MIN_SCORE = 80;
    public static final int COMPATIBLE_MAX_SCORE = 99;
    public static final int PARTIAL_MIN_SCORE = 50;
    public static final int PARTIAL_MAX_SCORE = 79;
    public static final int INCOMPATIBLE_MAX_SCORE = 49;
    public static final int WARNING_PENALTY = 8;
    public static final int ERROR_PENALTY = 35;

    private LibraryConstants() {
        throw new UnsupportedOperationException("Utility class");
    }
}