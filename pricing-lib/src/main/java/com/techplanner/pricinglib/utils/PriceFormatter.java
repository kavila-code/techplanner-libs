package com.techplanner.pricinglib.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormatter {

    public static String toCurrency(BigDecimal value) {

        NumberFormat formatter =
                NumberFormat.getCurrencyInstance(Locale.US);

        return formatter.format(value);
    }
}