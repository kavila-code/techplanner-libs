package com.techplanner.compatibilitylib.models;

import com.techplanner.compatibilitylib.exceptions.InvalidComponentException;

/**
 * Aggregates all components to be analyzed together.
 */
public record CompatibilityRequest(
        CPU cpu,
        GPU gpu,
        RAM ram,
        Motherboard motherboard,
        PSU psu,
        Storage storage
) {

    public CompatibilityRequest {
        if (cpu == null || gpu == null || ram == null || motherboard == null || psu == null || storage == null) {
            throw new InvalidComponentException("Todos los componentes de la solicitud son obligatorios");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private CPU cpu;
        private GPU gpu;
        private RAM ram;
        private Motherboard motherboard;
        private PSU psu;
        private Storage storage;

        private Builder() {
        }

        public Builder cpu(CPU cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder gpu(GPU gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder ram(RAM ram) {
            this.ram = ram;
            return this;
        }

        public Builder motherboard(Motherboard motherboard) {
            this.motherboard = motherboard;
            return this;
        }

        public Builder psu(PSU psu) {
            this.psu = psu;
            return this;
        }

        public Builder storage(Storage storage) {
            this.storage = storage;
            return this;
        }

        public CompatibilityRequest build() {
            return new CompatibilityRequest(cpu, gpu, ram, motherboard, psu, storage);
        }
    }
}