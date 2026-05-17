package com.techplanner.recommendationlib.model;

import java.math.BigDecimal;
import java.util.List;

public record RecommendationResult(
        UsageType usageType,
        List<ComponentRecommendation> components,
        BigDecimal estimatedTotalPrice,
        List<String> notes
) {
}
