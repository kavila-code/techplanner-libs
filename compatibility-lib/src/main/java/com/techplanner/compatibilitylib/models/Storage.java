package com.techplanner.compatibilitylib.models;

import com.techplanner.compatibilitylib.enums.StorageType;
import com.techplanner.compatibilitylib.utils.CompatibilityUtils;

import java.math.BigDecimal;

/**
 * Storage domain model.
 */
public record Storage(
        String brand,
        String model,
        StorageType type,
        String interfaceType,
        int capacityGb,
        int readSpeed,
        int writeSpeed,
        BigDecimal price
) {

    public Storage {
        brand = CompatibilityUtils.requireNonBlank(brand, "brand");
        model = CompatibilityUtils.requireNonBlank(model, "model");
        type = java.util.Objects.requireNonNull(type, "type");
        interfaceType = CompatibilityUtils.requireNonBlank(interfaceType, "interfaceType");
        capacityGb = CompatibilityUtils.requirePositive(capacityGb, "capacityGb");
        readSpeed = CompatibilityUtils.requirePositive(readSpeed, "readSpeed");
        writeSpeed = CompatibilityUtils.requirePositive(writeSpeed, "writeSpeed");
        price = CompatibilityUtils.requireNonNegative(price == null ? BigDecimal.ZERO : price, "price");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String brand;
        private String model;
        private StorageType type;
        private String interfaceType;
        private int capacityGb;
        private int readSpeed;
        private int writeSpeed;
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

        public Builder type(StorageType type) {
            this.type = type;
            return this;
        }

        public Builder interfaceType(String interfaceType) {
            this.interfaceType = interfaceType;
            return this;
        }

        public Builder capacityGb(int capacityGb) {
            this.capacityGb = capacityGb;
            return this;
        }

        public Builder readSpeed(int readSpeed) {
            this.readSpeed = readSpeed;
            return this;
        }

        public Builder writeSpeed(int writeSpeed) {
            this.writeSpeed = writeSpeed;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Storage build() {
            return new Storage(brand, model, type, interfaceType, capacityGb, readSpeed, writeSpeed, price);
        }
    }
}