package com.techplanner.compatibilitylib.integration;

import com.techplanner.compatibilitylib.analyzers.CompatibilityAnalyzer;
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

class CompatibilityAnalyzerIntegrationTest {

    @Test
    void shouldAnalyzeFullyCompatibleBuild() {
        CompatibilityRequest request = CompatibilityRequest.builder()
                .cpu(ComponentFactory.cpu("AMD", "Ryzen 7 7700", CpuSocket.AM5, 8, 16, 120, true, BigDecimal.valueOf(300)))
                .gpu(ComponentFactory.gpu("NVIDIA", "RTX 4070", "Ada", 12, 200, PcieVersion.PCIE_4, 240, BigDecimal.valueOf(700)))
                .ram(ComponentFactory.ram("Corsair", "Vengeance", RamType.DDR5, 32, 5600, 1.25d, 2, BigDecimal.valueOf(180)))
                .motherboard(ComponentFactory.motherboard("ASUS", "Prime", CpuSocket.AM5, RamType.DDR5, 128, 4, 6000, PcieVersion.PCIE_5, FormFactor.ATX, 4, 2, BigDecimal.valueOf(250)))
                .psu(ComponentFactory.psu("Corsair", "RM750", 750, "80+ Gold", true, FormFactor.ATX, BigDecimal.valueOf(160)))
                .storage(ComponentFactory.storage("Samsung", "990 Pro", StorageType.NVME, "M.2", 1000, 7450, 6900, BigDecimal.valueOf(140)))
                .build();

        CompatibilityResult result = new CompatibilityAnalyzer().analyze(request);

        assertTrue(result.compatible());
        assertEquals("PERFECT", result.status().name());
        assertEquals(100, result.compatibilityScore());
        assertTrue(result.warnings().isEmpty());
        assertTrue(result.errors().isEmpty());
        assertEquals(320, result.estimatedPowerConsumption());
        assertEquals(400, result.recommendedPsu());
    }
}