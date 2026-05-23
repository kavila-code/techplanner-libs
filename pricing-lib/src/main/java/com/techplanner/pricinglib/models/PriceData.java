package com.techplanner.pricinglib.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceData {

    private String productName;

    private BigDecimal basePrice;

    private BigDecimal discountPercentage;

    private BigDecimal taxPercentage;
}
