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

class RamMotherboardAnalyzerTest {

    @Test
    void shouldReportRamTypeMismatch() {
        CompatibilityRequest request = buildRequest(ComponentFactory.ram("Corsair", "Vengeance", RamType.DDR4, 32, 3200, 1.2d, 2, BigDecimal.valueOf(120)),
                ComponentFactory.motherboard("ASUS", "Prime", CpuSocket.AM5, RamType.DDR5, 128, 4, 6000, PcieVersion.PCIE_5, FormFactor.ATX, 4, 2, BigDecimal.valueOf(250)));

        CompatibilityResult.Builder builder = CompatibilityResult.builder();

        new RamMotherboardAnalyzer().analyze(request, builder);

        assertEquals("RAM tipo DDR4 no es compatible con motherboard tipo DDR5", builder.errors().get(0));
    }

    @Test
    void shouldReportRamCapacityAndSlotExcess() {
        CompatibilityRequest request = buildRequest(ComponentFactory.ram("G.Skill", "Trident Z", RamType.DDR5, 64, 7200, 1.35d, 4, BigDecimal.valueOf(220)),
                ComponentFactory.motherboard("ASUS", "Prime", CpuSocket.AM5, RamType.DDR5, 128, 2, 6000, PcieVersion.PCIE_5, FormFactor.ATX, 4, 2, BigDecimal.valueOf(250)));

        CompatibilityResult.Builder builder = CompatibilityResult.builder();

        new RamMotherboardAnalyzer().analyze(request, builder);

        assertEquals(2, builder.errors().size());
        assertTrue(builder.warnings().contains("RAM velocidad 7200MHz supera la velocidad soportada por la motherboard 6000MHz"));
    }

    private CompatibilityRequest buildRequest(com.techplanner.compatibilitylib.models.RAM ram, com.techplanner.compatibilitylib.models.Motherboard motherboard) {
        return CompatibilityRequest.builder()
                .cpu(ComponentFactory.cpu("AMD", "Ryzen 7 7700", CpuSocket.AM5, 8, 16, 120, true, BigDecimal.valueOf(300)))
                .gpu(ComponentFactory.gpu("NVIDIA", "RTX 4070", "Ada", 12, 200, PcieVersion.PCIE_4, 240, BigDecimal.valueOf(700)))
                .ram(ram)
                .motherboard(motherboard)
                .psu(ComponentFactory.psu("Corsair", "RM750", 750, "80+ Gold", true, FormFactor.ATX, BigDecimal.valueOf(160)))
                .storage(ComponentFactory.storage("Samsung", "990 Pro", StorageType.NVME, "M.2", 1000, 7450, 6900, BigDecimal.valueOf(140)))
                .build();
    }
}