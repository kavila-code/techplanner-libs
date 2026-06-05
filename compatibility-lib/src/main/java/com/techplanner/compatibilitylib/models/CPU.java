package com.techplanner.compatibilitylib.models;

import com.techplanner.compatibilitylib.enums.CpuSocket;
import com.techplanner.compatibilitylib.exceptions.InvalidComponentException;
import com.techplanner.compatibilitylib.utils.CompatibilityUtils;

import java.math.BigDecimal;

/**
 * CPU domain model.
 */
public record CPU(
        String brand,
        String model,
        CpuSocket socket,
        int cores,
        int threads,
        int tdp,
        boolean integratedGraphics,
        BigDecimal price
) {

    public CPU {
        brand = CompatibilityUtils.requireNonBlank(brand, "brand");
        model = CompatibilityUtils.requireNonBlank(model, "model");
        socket = java.util.Objects.requireNonNull(socket, "socket");
        cores = CompatibilityUtils.requirePositive(cores, "cores");
        threads = CompatibilityUtils.requirePositive(threads, "threads");
        tdp = CompatibilityUtils.requirePositive(tdp, "tdp");
        price = CompatibilityUtils.requireNonNegative(price == null ? BigDecimal.ZERO : price, "price");

        if (threads < cores) {
            throw new InvalidComponentException("threads no puede ser menor que cores");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String brand;
        private String model;
        private CpuSocket socket;
        private int cores;
        private int threads;
        private int tdp;
        private boolean integratedGraphics;
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

        public Builder cores(int cores) {
            this.cores = cores;
            return this;
        }

        public Builder threads(int threads) {
            this.threads = threads;
            return this;
        }

        public Builder tdp(int tdp) {
            this.tdp = tdp;
            return this;
        }

        public Builder integratedGraphics(boolean integratedGraphics) {
            this.integratedGraphics = integratedGraphics;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public CPU build() {
            return new CPU(brand, model, socket, cores, threads, tdp, integratedGraphics, price);
        }
    }
}