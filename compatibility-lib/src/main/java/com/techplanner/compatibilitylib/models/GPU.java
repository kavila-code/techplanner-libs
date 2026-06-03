package com.techplanner.compatibilitylib.models;

import com.techplanner.compatibilitylib.enums.PcieVersion;
import com.techplanner.compatibilitylib.utils.CompatibilityUtils;

import java.math.BigDecimal;

/**
 * GPU domain model.
 */
public record GPU(
        String brand,
        String model,
        String chipset,
        int vram,
        int recommendedWattage,
        PcieVersion pcieVersion,
        int lengthMm,
        BigDecimal price
) {

    public GPU {
        brand = CompatibilityUtils.requireNonBlank(brand, "brand");
        model = CompatibilityUtils.requireNonBlank(model, "model");
        chipset = CompatibilityUtils.requireNonBlank(chipset, "chipset");
        vram = CompatibilityUtils.requirePositive(vram, "vram");
        recommendedWattage = CompatibilityUtils.requirePositive(recommendedWattage, "recommendedWattage");
        pcieVersion = java.util.Objects.requireNonNull(pcieVersion, "pcieVersion");
        lengthMm = CompatibilityUtils.requirePositive(lengthMm, "lengthMm");
        price = CompatibilityUtils.requireNonNegative(price == null ? BigDecimal.ZERO : price, "price");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String brand;
        private String model;
        private String chipset;
        private int vram;
        private int recommendedWattage;
        private PcieVersion pcieVersion;
        private int lengthMm;
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

        public Builder chipset(String chipset) {
            this.chipset = chipset;
            return this;
        }

        public Builder vram(int vram) {
            this.vram = vram;
            return this;
        }

        public Builder recommendedWattage(int recommendedWattage) {
            this.recommendedWattage = recommendedWattage;
            return this;
        }

        public Builder pcieVersion(PcieVersion pcieVersion) {
            this.pcieVersion = pcieVersion;
            return this;
        }

        public Builder lengthMm(int lengthMm) {
            this.lengthMm = lengthMm;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public GPU build() {
            return new GPU(brand, model, chipset, vram, recommendedWattage, pcieVersion, lengthMm, price);
        }
    }
}