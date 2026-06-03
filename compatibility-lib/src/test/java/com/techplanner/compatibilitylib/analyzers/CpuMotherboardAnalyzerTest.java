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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CpuMotherboardAnalyzerTest {

    @Test
    void shouldReportIncompatibleSocketMismatch() {
        CompatibilityRequest request = buildRequest(CpuSocket.AM5, CpuSocket.LGA1700);
        CompatibilityResult.Builder builder = CompatibilityResult.builder();

        new CpuMotherboardAnalyzer().analyze(request, builder);

        assertFalse(builder.errors().isEmpty());
        assertEquals("CPU socket AM5 no es compatible con motherboard socket LGA1700", builder.errors().get(0));
        assertTrue(builder.recommendations().isEmpty());
    }

    private CompatibilityRequest buildRequest(CpuSocket cpuSocket, CpuSocket motherboardSocket) {
        return CompatibilityRequest.builder()
                .cpu(ComponentFactory.cpu("AMD", "Ryzen 7 7700", cpuSocket, 8, 16, 120, true, BigDecimal.valueOf(300)))
                .gpu(ComponentFactory.gpu("NVIDIA", "RTX 4070", "Ada", 12, 200, PcieVersion.PCIE_4, 240, BigDecimal.valueOf(700)))
                .ram(ComponentFactory.ram("Corsair", "Vengeance", RamType.DDR5, 32, 5600, 1.25d, 2, BigDecimal.valueOf(180)))
                .motherboard(ComponentFactory.motherboard("ASUS", "Prime", motherboardSocket, RamType.DDR5, 128, 4, 6000, PcieVersion.PCIE_5, FormFactor.ATX, 4, 2, BigDecimal.valueOf(250)))
                .psu(ComponentFactory.psu("Corsair", "RM750", 750, "80+ Gold", true, FormFactor.ATX, BigDecimal.valueOf(160)))
                .storage(ComponentFactory.storage("Samsung", "990 Pro", StorageType.NVME, "M.2", 1000, 7450, 6900, BigDecimal.valueOf(140)))
                .build();
    }
}