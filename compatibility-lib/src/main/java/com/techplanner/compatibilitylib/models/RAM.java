package com.techplanner.compatibilitylib.models;

import com.techplanner.compatibilitylib.enums.RamType;
import com.techplanner.compatibilitylib.utils.CompatibilityUtils;

import java.math.BigDecimal;

/**
 * RAM domain model.
 */
public record RAM(
        String brand,
        String model,
        RamType type,
        int capacityGb,
        int speedMHz,
        double voltage,
        int sticks,
        BigDecimal price
) {

    public RAM {
        brand = CompatibilityUtils.requireNonBlank(brand, "brand");
        model = CompatibilityUtils.requireNonBlank(model, "model");
        type = java.util.Objects.requireNonNull(type, "type");
        capacityGb = CompatibilityUtils.requirePositive(capacityGb, "capacityGb");
        speedMHz = CompatibilityUtils.requirePositive(speedMHz, "speedMHz");
        voltage = CompatibilityUtils.requirePositive(voltage, "voltage");
        sticks = CompatibilityUtils.requirePositive(sticks, "sticks");
        price = CompatibilityUtils.requireNonNegative(price == null ? BigDecimal.ZERO : price, "price");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String brand;
        private String model;
        private RamType type;
        private int capacityGb;
        private int speedMHz;
        private double voltage;
        private int sticks;
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

        public Builder type(RamType type) {
            this.type = type;
            return this;
        }

        public Builder capacityGb(int capacityGb) {
            this.capacityGb = capacityGb;
            return this;
        }

        public Builder speedMHz(int speedMHz) {
            this.speedMHz = speedMHz;
            return this;
        }

        public Builder voltage(double voltage) {
            this.voltage = voltage;
            return this;
        }

        public Builder sticks(int sticks) {
            this.sticks = sticks;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public RAM build() {
            return new RAM(brand, model, type, capacityGb, speedMHz, voltage, sticks, price);
        }
    }
}