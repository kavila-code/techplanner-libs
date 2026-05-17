package com.techplanner.recommendationlib.model;

import java.math.BigDecimal;

public record RecommendationRequest(String usageType, BigDecimal budget) {
}
