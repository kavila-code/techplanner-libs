package com.techplanner.recommendationlib.validation;

import com.techplanner.recommendationlib.model.RecommendationRequest;

import java.math.BigDecimal;

public final class RecommendationValidator {

    private RecommendationValidator() {
    }

    public static void validate(RecommendationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud de recomendación no puede ser nula.");
        }

        if (request.usageType() == null || request.usageType().isBlank()) {
            throw new IllegalArgumentException("El tipo de uso es obligatorio.");
        }

        BigDecimal budget = request.budget();
        if (budget != null && budget.signum() < 0) {
            throw new IllegalArgumentException("El presupuesto no puede ser negativo.");
        }
    }
}
