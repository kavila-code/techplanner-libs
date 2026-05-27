package com.techplanner.pricingnative.bridge;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Bridge JNA hacia la librería nativa.
 */
public interface PricingNativeLibrary extends Library {

    /**
     * "pricing"
     * buscará:
     *   pricing.dll (Windows)
     *   libpricing.so (Linux)
     */
    PricingNativeLibrary INSTANCE =
            Native.load("pricing", PricingNativeLibrary.class);

    /* Funciones nativas */

    double calculate_price(double basePrice, double tax);

    int apply_discount(int amount, int percentage);

    int fibonacci(int n);
}