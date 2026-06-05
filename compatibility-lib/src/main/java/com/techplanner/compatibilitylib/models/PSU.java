package com.techplanner.compatibilitylib.models;

import com.techplanner.compatibilitylib.enums.FormFactor;
import com.techplanner.compatibilitylib.utils.CompatibilityUtils;

import java.math.BigDecimal;

/**
 * Power supply domain model.
 */
public record PSU(
        String brand,
        String model,
        int wattage,
        String efficiency,
        boolean modular,
        FormFactor formFactor,
        BigDecimal price
) {

    public PSU {
        brand = CompatibilityUtils.requireNonBlank(brand, "brand");
        model = CompatibilityUtils.requireNonBlank(model, "model");
        wattage = CompatibilityUtils.requirePositive(wattage, "wattage");
        efficiency = CompatibilityUtils.requireNonBlank(efficiency, "efficiency");
        formFactor = java.util.Objects.requireNonNull(formFactor, "formFactor");
        price = CompatibilityUtils.requireNonNegative(price == null ? BigDecimal.ZERO : price, "price");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String brand;
        private String model;
        private int wattage;
        private String efficiency;
        private boolean modular;
        private FormFactor formFactor;
        private BigDecimal price = BigDecimal.ZERO;

        private Builder() {
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder wattage(int wattage) {
            this.wattage = wattage;
            return this;
        }

        public Builder efficiency(String efficiency) {
            this.efficiency = efficiency;
            return this;
        }

        public Builder modular(boolean modular) {
            this.modular = modular;
            return this;
        }

        public Builder formFactor(FormFactor formFactor) {
            this.formFactor = formFactor;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public PSU build() {
            return new PSU(brand, model, wattage, efficiency, modular, formFactor, price);
        }
    }
}