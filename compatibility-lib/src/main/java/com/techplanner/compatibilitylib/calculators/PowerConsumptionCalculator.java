package com.techplanner.compatibilitylib.calculators;

import com.techplanner.compatibilitylib.config.LibraryConstants;
import com.techplanner.compatibilitylib.models.CompatibilityRequest;

/**
 * Calculates estimated power usage and recommended PSU wattage.
 */
public final class PowerConsumptionCalculator {

    public int calculateEstimatedPowerConsumption(CompatibilityRequest request) {
        return request.cpu().tdp() + request.gpu().recommendedWattage();
    }

    public int calculateRecommendedPsu(int estimatedPowerConsumption) {
        return com.techplanner.compatibilitylib.utils.CompatibilityUtils.applyPercentageMargin(
                estimatedPowerConsumption,
                LibraryConstants.POWER_MARGIN_PERCENTAGE
        );
    }
}