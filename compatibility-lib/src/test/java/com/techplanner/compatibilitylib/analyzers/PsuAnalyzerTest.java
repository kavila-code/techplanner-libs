package com.techplanner.compatibilitylib.analyzers;

import com.techplanner.compatibilitylib.enums.CpuSocket;
import com.techplanner.compatibilitylib.enums.FormFactor;
import com.techplanner.compatibilitylib.enums.PcieVersion;
import com.techplanner.compatibilitylib.enums.RamType;
import com.techplanner.compatibilitylib.enums.StorageType;
import com.techplanner.compatibilitylib.factory.ComponentFactory;
import com.techplanner.compatibilitylib.models.CompatibilityRequest;
import com.techplanner.compatibilitylib.models.CompatibilityResult;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PsuAnalyzerTest {

    @Test
    void shouldReportInsufficientPsu() {
        CompatibilityRequest request = CompatibilityRequest.builder()
                .cpu(ComponentFactory.cpu("AMD", "Ryzen 9 7900X", CpuSocket.AM5, 12, 24, 170, false, BigDecimal.valueOf(450)))
                .gpu(ComponentFactory.gpu("NVIDIA", "RTX 4090", "Ada", 24, 320, PcieVersion.PCIE_5, 340, BigDecimal.valueOf(2000)))
                .ram(ComponentFactory.ram("Corsair", "Vengeance", RamType.DDR5, 32, 5600, 1.25d, 2, BigDecimal.valueOf(180)))
                .motherboard(ComponentFactory.motherboard("ASUS", "Prime", CpuSocket.AM5, RamType.DDR5, 128, 4, 6000, PcieVersion.PCIE_5, FormFactor.ATX, 4, 2, BigDecimal.valueOf(250)))
                .psu(ComponentFactory.psu("Generic", "450W", 450, "80+ Bronze", false, FormFactor.ATX, BigDecimal.valueOf(70)))
                .storage(ComponentFactory.storage("Samsung", "990 Pro", StorageType.NVME, "M.2", 1000, 7450, 6900, BigDecimal.valueOf(140)))
                .build();

        CompatibilityResult.Builder builder = CompatibilityResult.builder();

        new PsuAnalyzer().analyze(request, builder);

        assertEquals(490, builder.estimatedPowerConsumption());
        assertEquals(613, builder.recommendedPsu());
        assertTrue(builder.errors().contains("PSU 450W es insuficiente para el consumo estimado de 490W"));
    }
}