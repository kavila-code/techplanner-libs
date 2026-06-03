package com.techplanner.compatibilitylib.calculators;

import com.techplanner.compatibilitylib.enums.CompatibilityStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompatibilityScoreCalculatorTest {

    private final CompatibilityScoreCalculator calculator = new CompatibilityScoreCalculator();

    @Test
    void shouldReturnPerfectScore() {
        assertEquals(100, calculator.calculateScore(0, 0, CompatibilityStatus.PERFECT));
    }

    @Test
    void shouldReturnCompatibleScoreWithinRange() {
        assertEquals(84, calculator.calculateScore(2, 0, CompatibilityStatus.COMPATIBLE));
    }

    @Test
    void shouldReturnPartialScoreWithinRange() {
        assertEquals(76, calculator.calculateScore(3, 0, CompatibilityStatus.PARTIALLY_COMPATIBLE));
    }

    @Test
    void shouldCapIncompatibleScore() {
        assertEquals(49, calculator.calculateScore(0, 1, CompatibilityStatus.INCOMPATIBLE));
    }
}