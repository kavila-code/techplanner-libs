package com.techplanner.compatibilitylib.analyzers;

import com.techplanner.compatibilitylib.calculators.PowerConsumptionCalculator;
import com.techplanner.compatibilitylib.models.CompatibilityRequest;
import com.techplanner.compatibilitylib.models.CompatibilityResult;

/**
 * Validates PSU capacity against the system power requirements.
 */
public class PsuAnalyzer {

    private final PowerConsumptionCalculator powerConsumptionCalculator;

    public PsuAnalyzer() {
        this(new PowerConsumptionCalculator());
    }

    public PsuAnalyzer(PowerConsumptionCalculator powerConsumptionCalculator) {
        this.powerConsumptionCalculator = powerConsumptionCalculator;
    }

    public int estimatePowerConsumption(CompatibilityRequest request) {
        return powerConsumptionCalculator.calculateEstimatedPowerConsumption(request);
    }

    public int recommendedPsu(CompatibilityRequest request) {
        return powerConsumptionCalculator.calculateRecommendedPsu(estimatePowerConsumption(request));
    }

    public void analyze(CompatibilityRequest request, CompatibilityResult.Builder resultBuilder) {
        int estimatedPower = estimatePowerConsumption(request);
        int recommendedPsu = recommendedPsu(request);

        resultBuilder.estimatedPowerConsumption(estimatedPower);
        resultBuilder.recommendedPsu(recommendedPsu);

        if (request.psu().wattage() < estimatedPower) {
            resultBuilder.addError("PSU %dW es insuficiente para el consumo estimado de %dW"
                    .formatted(request.psu().wattage(), estimatedPower));
            return;
        }

        if (request.psu().wattage() < recommendedPsu) {
            resultBuilder.addWarning("PSU %dW cubre el consumo estimado pero no deja margen de seguridad del 25%%"
                    .formatted(request.psu().wattage()));
            return;
        }

        resultBuilder.addRecommendation("PSU %dW proporciona margen adecuado para el sistema".formatted(request.psu().wattage()));
    }
}