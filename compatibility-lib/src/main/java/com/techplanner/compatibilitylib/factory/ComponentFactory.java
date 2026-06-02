package com.techplanner.compatibilitylib.factory;

import com.techplanner.compatibilitylib.enums.CpuSocket;
import com.techplanner.compatibilitylib.enums.FormFactor;
import com.techplanner.compatibilitylib.enums.PcieVersion;
import com.techplanner.compatibilitylib.enums.RamType;
import com.techplanner.compatibilitylib.enums.StorageType;
import com.techplanner.compatibilitylib.models.CPU;
import com.techplanner.compatibilitylib.models.GPU;
import com.techplanner.compatibilitylib.models.Motherboard;
import com.techplanner.compatibilitylib.models.PSU;
import com.techplanner.compatibilitylib.models.RAM;
import com.techplanner.compatibilitylib.models.Storage;

import java.math.BigDecimal;

/**
 * Convenience factory for building component instances.
 */
public final class ComponentFactory {

    private ComponentFactory() {
    }

    public static CPU cpu(String brand, String model, CpuSocket socket, int cores, int threads, int tdp, boolean integratedGraphics, BigDecimal price) {
        return CPU.builder()
                .brand(brand)
                .model(model)
                .socket(socket)
                .cores(cores)
                .threads(threads)
                .tdp(tdp)
                .integratedGraphics(integratedGraphics)
                .price(price)
                .build();
    }

    public static GPU gpu(String brand, String model, String chipset, int vram, int recommendedWattage, PcieVersion pcieVersion, int lengthMm, BigDecimal price) {
        return GPU.builder()
                .brand(brand)
                .model(model)
                .chipset(chipset)
                .vram(vram)
                .recommendedWattage(recommendedWattage)
                .pcieVersion(pcieVersion)
                .lengthMm(lengthMm)
                .price(price)
                .build();
    }

    public static RAM ram(String brand, String model, RamType type, int capacityGb, int speedMHz, double voltage, int sticks, BigDecimal price) {
        return RAM.builder()
                .brand(brand)
                .model(model)
                .type(type)
                .capacityGb(capacityGb)
                .speedMHz(speedMHz)
                .voltage(voltage)
                .sticks(sticks)
                .price(price)
                .build();
    }

    public static Motherboard motherboard(String brand, String model, CpuSocket socket, RamType ramType, int maxRam, int ramSlots, int supportedRamSpeed, PcieVersion pcieVersion, FormFactor formFactor, int sataPorts, int m2Slots, BigDecimal price) {
        return Motherboard.builder()
                .brand(brand)
                .model(model)
                .socket(socket)
                .ramType(ramType)
                .maxRam(maxRam)
                .ramSlots(ramSlots)
                .supportedRamSpeed(supportedRamSpeed)
                .pcieVersion(pcieVersion)
                .formFactor(formFactor)
                .sataPorts(sataPorts)
                .m2Slots(m2Slots)
                .price(price)
                .build();
    }

    public static PSU psu(String brand, String model, int wattage, String efficiency, boolean modular, FormFactor formFactor, BigDecimal price) {
        return PSU.builder()
                .brand(brand)
                .model(model)
                .wattage(wattage)
                .efficiency(efficiency)
                .modular(modular)
                .formFactor(formFactor)
                .price(price)
                .build();
    }

    public static Storage storage(String brand, String model, StorageType type, String interfaceType, int capacityGb, int readSpeed, int writeSpeed, BigDecimal price) {
        return Storage.builder()
                .brand(brand)
                .model(model)
                .type(type)
                .interfaceType(interfaceType)
                .capacityGb(capacityGb)
                .readSpeed(readSpeed)
                .writeSpeed(writeSpeed)
                .price(price)
                .build();
    }
}