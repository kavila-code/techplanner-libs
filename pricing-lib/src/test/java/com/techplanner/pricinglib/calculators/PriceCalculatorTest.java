package com.techplanner.pricinglib.calculators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Unit tests - PriceCalculator")
class PriceCalculatorTest {

    @Test
    @DisplayName("calculateFinalPrice should calculate correctly")
    void calculateFinalPrice_shouldCalculateCorrectly() {

        BigDecimal result =
                PriceCalculator.calculateFinalPrice(
                        new BigDecimal("100"),
                        new BigDecimal("10"),
                        new BigDecimal("19")
                );

        BigDecimal expected =
                new BigDecimal("107.10");

        assertEquals(
                0,
                expected.compareTo(result)
        );
    }
}