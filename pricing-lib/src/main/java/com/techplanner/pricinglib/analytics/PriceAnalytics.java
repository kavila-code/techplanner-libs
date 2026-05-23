package com.techplanner.pricinglib.analytics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PriceAnalytics {

    public static BigDecimal average(List<BigDecimal> prices) {

        if (prices == null || prices.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = prices.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(
                BigDecimal.valueOf(prices.size()),
                2,
                RoundingMode.HALF_UP
        );
    }

    public static BigDecimal max(List<BigDecimal> prices) {

        return prices.stream()
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal min(List<BigDecimal> prices) {

        return prices.stream()
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }
}