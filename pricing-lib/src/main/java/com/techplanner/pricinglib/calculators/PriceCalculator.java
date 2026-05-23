package com.techplanner.pricinglib.calculators;

import com.techplanner.pricinglib.exceptions.InvalidPriceException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceCalculator {

    public static BigDecimal applyDiscount(
            BigDecimal basePrice,
            BigDecimal discountPercentage
    ) {

        validatePrice(basePrice);

        BigDecimal discount =
                basePrice.multiply(
                        discountPercentage.divide(
                                BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP
                        )
                );

        return basePrice.subtract(discount);
    }

    public static BigDecimal applyTax(
            BigDecimal price,
            BigDecimal taxPercentage
    ) {

        validatePrice(price);

        BigDecimal tax =
                price.multiply(
                        taxPercentage.divide(
                                BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP
                        )
                );

        return price.add(tax);
    }

    public static BigDecimal calculateFinalPrice(
            BigDecimal basePrice,
            BigDecimal discountPercentage,
            BigDecimal taxPercentage
    ) {

        BigDecimal discountedPrice =
                applyDiscount(basePrice, discountPercentage);

        return applyTax(discountedPrice, taxPercentage);
    }

    private static void validatePrice(BigDecimal price) {

        if (price == null ||
                price.compareTo(BigDecimal.ZERO) < 0) {

            throw new InvalidPriceException(
                    "Price cannot be null or negative"
            );
        }
    }
}