package com.techplanner.pricingnative.service;

import com.techplanner.pricingnative.bridge.PricingNativeLibrary;
import org.springframework.stereotype.Service;

@Service
public class PricingNativeService {

    private final PricingNativeLibrary lib =
            PricingNativeLibrary.INSTANCE;

    public double calculatePrice(double basePrice, double tax) {
        return lib.calculate_price(basePrice, tax);
    }

    public int applyDiscount(int amount, int percentage) {
        return lib.apply_discount(amount, percentage);
    }

    public int fibonacci(int n) {

        if (n < 0) {
            throw new IllegalArgumentException(
                    "n no puede ser negativo");
        }

        return lib.fibonacci(n);
    }
}