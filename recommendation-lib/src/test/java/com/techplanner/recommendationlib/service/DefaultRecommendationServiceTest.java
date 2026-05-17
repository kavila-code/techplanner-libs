package com.techplanner.recommendationlib.service;

import com.techplanner.recommendationlib.model.RecommendationRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultRecommendationServiceTest {

    private final DefaultRecommendationService service = new DefaultRecommendationService();

    @Test
    void recommendGamingShouldReturnGamingBundle() {
        var result = service.recommend(new RecommendationRequest("gaming", BigDecimal.valueOf(3000)));

        assertEquals(7, result.components().size());
        assertEquals("GAMING", result.usageType().name());
        assertTrue(result.estimatedTotalPrice().compareTo(BigDecimal.valueOf(1570)) == 0);
        assertTrue(result.notes().contains("Configuración dentro del presupuesto estimado."));
    }

    @Test
    void recommendShouldRejectNegativeBudget() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.recommend(new RecommendationRequest("office", BigDecimal.valueOf(-1))));

        assertTrue(exception.getMessage().contains("presupuesto no puede ser negativo"));
    }
}
