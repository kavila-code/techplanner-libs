package com.techplanner.compatibilitylib.calculators;

import com.techplanner.compatibilitylib.enums.CpuSocket;
import com.techplanner.compatibilitylib.enums.FormFactor;
import com.techplanner.compatibilitylib.enums.PcieVersion;
import com.techplanner.compatibilitylib.enums.RamType;
import com.techplanner.compatibilitylib.enums.StorageType;
import com.techplanner.compatibilitylib.factory.ComponentFactory;
import com.techplanner.compatibilitylib.models.CompatibilityRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PowerConsumptionCalculatorTest {

    @Test
    void shouldCalculateEstimatedAndRecommendedPower() {
        CompatibilityRequest request = CompatibilityRequest.builder()
                .cpu(ComponentFactory.cpu("AMD", "Ryzen 7 7700", CpuSocket.AM5, 8, 16, 120, true, BigDecimal.valueOf(300)))
                .gpu(ComponentFactory.gpu("NVIDIA", "RTX 4080", "Ada", 16, 320, PcieVersion.PCIE_5, 304, BigDecimal.valueOf(1200)))
                .ram(ComponentFactory.ram("Corsair", "Vengeance", RamType.DDR5, 32, 5600, 1.25d, 2, BigDecimal.valueOf(180)))
                .motherboard(ComponentFactory.motherboard("ASUS", "B650", CpuSocket.AM5, RamType.DDR5, 128, 4, 6000, PcieVersion.PCIE_5, FormFactor.ATX, 4, 2, BigDecimal.valueOf(250)))
                .psu(ComponentFactory.psu("Corsair", "RM750", 750, "80+ Gold", true, FormFactor.ATX, BigDecimal.valueOf(160)))
                .storage(ComponentFactory.storage("Samsung", "990 Pro", StorageType.NVME, "M.2", 1000, 7450, 6900, BigDecimal.valueOf(140)))
                .build();

        PowerConsumptionCalculator calculator = new PowerConsumptionCalculator();

        assertEquals(440, calculator.calculateEstimatedPowerConsumption(request));
        assertEquals(550, calculator.calculateRecommendedPsu(440));
    }
}