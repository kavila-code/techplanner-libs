package com.techplanner.compatibilitylib.calculators;

import com.techplanner.compatibilitylib.config.LibraryConstants;
import com.techplanner.compatibilitylib.enums.CompatibilityStatus;

/**
 * Calculates the final compatibility score.
 */
public final class CompatibilityScoreCalculator {

    public int calculateScore(int warningCount, int errorCount, CompatibilityStatus status) {
        int rawScore = LibraryConstants.PERFECT_SCORE
                - (warningCount * LibraryConstants.WARNING_PENALTY)
                - (errorCount * LibraryConstants.ERROR_PENALTY);

        rawScore = Math.max(LibraryConstants.SCORE_MIN, Math.min(LibraryConstants.SCORE_MAX, rawScore));

        return switch (status) {
            case PERFECT -> LibraryConstants.PERFECT_SCORE;
            case COMPATIBLE -> Math.max(LibraryConstants.COMPATIBLE_MIN_SCORE,
                    Math.min(LibraryConstants.COMPATIBLE_MAX_SCORE, rawScore));
            case PARTIALLY_COMPATIBLE -> Math.max(LibraryConstants.PARTIAL_MIN_SCORE,
                    Math.min(LibraryConstants.PARTIAL_MAX_SCORE, rawScore));
            case INCOMPATIBLE -> Math.min(LibraryConstants.INCOMPATIBLE_MAX_SCORE, rawScore);
        };
    }
}