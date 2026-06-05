package com.techplanner.compatibilitylib.models;

import com.techplanner.compatibilitylib.enums.CpuSocket;
import com.techplanner.compatibilitylib.enums.FormFactor;
import com.techplanner.compatibilitylib.enums.PcieVersion;
import com.techplanner.compatibilitylib.enums.RamType;
import com.techplanner.compatibilitylib.utils.CompatibilityUtils;

import java.math.BigDecimal;

/**
 * Motherboard domain model.
 */
public record Motherboard(
        String brand,
        String model,
        CpuSocket socket,
        RamType ramType,
        int maxRam,
        int ramSlots,
        int supportedRamSpeed,
        PcieVersion pcieVersion,
        FormFactor formFactor,
        int sataPorts,
        int m2Slots,
        BigDecimal price
) {

    public Motherboard {
        brand = CompatibilityUtils.requireNonBlank(brand, "brand");
        model = CompatibilityUtils.requireNonBlank(model, "model");
        socket = java.util.Objects.requireNonNull(socket, "socket");
        ramType = java.util.Objects.requireNonNull(ramType, "ramType");
        maxRam = CompatibilityUtils.requirePositive(maxRam, "maxRam");
        ramSlots = CompatibilityUtils.requirePositive(ramSlots, "ramSlots");
        supportedRamSpeed = CompatibilityUtils.requirePositive(supportedRamSpeed, "supportedRamSpeed");
        pcieVersion = java.util.Objects.requireNonNull(pcieVersion, "pcieVersion");
        formFactor = java.util.Objects.requireNonNull(formFactor, "formFactor");
        sataPorts = CompatibilityUtils.requireNonNegative(sataPorts, "sataPorts");
        m2Slots = CompatibilityUtils.requireNonNegative(m2Slots, "m2Slots");
        price = CompatibilityUtils.requireNonNegative(price == null ? BigDecimal.ZERO : price, "price");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String brand;
        private String model;
        private CpuSocket socket;
        private RamType ramType;
        private int maxRam;
        private int ramSlots;
        private int supportedRamSpeed;
        private PcieVersion pcieVersion;
        private FormFactor formFactor;
        private int sataPorts;
        private int m2Slots;
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

        public Builder socket(CpuSocket socket) {
            this.socket = socket;
            return this;
        }

        public Builder ramType(RamType ramType) {
            this.ramType = ramType;
            return this;
        }

        public Builder maxRam(int maxRam) {
            this.maxRam = maxRam;
            return this;
        }

        public Builder ramSlots(int ramSlots) {
            this.ramSlots = ramSlots;
            return this;
        }

        public Builder supportedRamSpeed(int supportedRamSpeed) {
            this.supportedRamSpeed = supportedRamSpeed;
            return this;
        }

        public Builder pcieVersion(PcieVersion pcieVersion) {
            this.pcieVersion = pcieVersion;
            return this;
        }

        public Builder formFactor(FormFactor formFactor) {
            this.formFactor = formFactor;
            return this;
        }

        public Builder sataPorts(int sataPorts) {
            this.sataPorts = sataPorts;
            return this;
        }

        public Builder m2Slots(int m2Slots) {
            this.m2Slots = m2Slots;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Motherboard build() {
            return new Motherboard(brand, model, socket, ramType, maxRam, ramSlots, supportedRamSpeed, pcieVersion, formFactor, sataPorts, m2Slots, price);
        }
    }
}