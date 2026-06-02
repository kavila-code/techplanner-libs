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

import static org.junit.jupiter.api.Assertions.assertTrue;

class GpuMotherboardAnalyzerTest {

    @Test
    void shouldWarnWhenGpuIsNewerThanMotherboardPcie() {
        CompatibilityRequest request = CompatibilityRequest.builder()
                .cpu(ComponentFactory.cpu("AMD", "Ryzen 7 7700", CpuSocket.AM5, 8, 16, 120, true, BigDecimal.valueOf(300)))
                .gpu(ComponentFactory.gpu("NVIDIA", "RTX 5090", "Blackwell", 32, 450, PcieVersion.PCIE_5, 340, BigDecimal.valueOf(2000)))
                .ram(ComponentFactory.ram("Corsair", "Vengeance", RamType.DDR5, 32, 5600, 1.25d, 2, BigDecimal.valueOf(180)))
                .motherboard(ComponentFactory.motherboard("ASUS", "Prime", CpuSocket.AM5, RamType.DDR5, 128, 4, 6000, PcieVersion.PCIE_4, FormFactor.ATX, 4, 2, BigDecimal.valueOf(250)))
                .psu(ComponentFactory.psu("Corsair", "RM850", 850, "80+ Gold", true, FormFactor.ATX, BigDecimal.valueOf(180)))
                .storage(ComponentFactory.storage("Samsung", "990 Pro", StorageType.NVME, "M.2", 1000, 7450, 6900, BigDecimal.valueOf(140)))
                .build();

        CompatibilityResult.Builder builder = CompatibilityResult.builder();

        new GpuMotherboardAnalyzer().analyze(request, builder);

        assertTrue(builder.warnings().contains("GPU PCIe PCIE_5 en motherboard PCIe PCIE_4 es compatible pero puede reducir rendimiento"));
    }
}