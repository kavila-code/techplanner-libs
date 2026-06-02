package com.techplanner.compatibilitylib.models;

import com.techplanner.compatibilitylib.enums.CpuSocket;
import com.techplanner.compatibilitylib.enums.FormFactor;
import com.techplanner.compatibilitylib.enums.PcieVersion;
import com.techplanner.compatibilitylib.enums.RamType;
import com.techplanner.compatibilitylib.enums.StorageType;
import com.techplanner.compatibilitylib.exceptions.InvalidComponentException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ModelValidationTest {

    @Test
    void shouldRejectCpuWithMoreCoresThanThreads() {
        assertThrows(InvalidComponentException.class, () -> CPU.builder()
                .brand("AMD")
                .model("Ryzen")
                .socket(CpuSocket.AM5)
                .cores(8)
                .threads(4)
                .tdp(120)
                .integratedGraphics(true)
                .price(BigDecimal.valueOf(300))
                .build());
    }

    @Test
    void shouldRejectMissingRequestComponent() {
        assertThrows(InvalidComponentException.class, () -> CompatibilityRequest.builder()
                .cpu(CPU.builder().brand("AMD").model("Ryzen").socket(CpuSocket.AM5).cores(8).threads(16).tdp(120).integratedGraphics(true).price(BigDecimal.valueOf(300)).build())
                .build());
    }

    @Test
    void shouldRejectNegativeStorageCapacity() {
        assertThrows(InvalidComponentException.class, () -> Storage.builder()
                .brand("Samsung")
                .model("990 Pro")
                .type(StorageType.NVME)
                .interfaceType("M.2")
                .capacityGb(-1)
                .readSpeed(7000)
                .writeSpeed(5000)
                .price(BigDecimal.valueOf(140))
                .build());
    }
}