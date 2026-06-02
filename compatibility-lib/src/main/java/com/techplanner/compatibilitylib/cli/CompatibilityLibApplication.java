package com.techplanner.compatibilitylib.cli;

import com.techplanner.compatibilitylib.analyzers.CompatibilityAnalyzer;
import com.techplanner.compatibilitylib.enums.CpuSocket;
import com.techplanner.compatibilitylib.enums.FormFactor;
import com.techplanner.compatibilitylib.enums.PcieVersion;
import com.techplanner.compatibilitylib.enums.RamType;
import com.techplanner.compatibilitylib.enums.StorageType;
import com.techplanner.compatibilitylib.factory.ComponentFactory;
import com.techplanner.compatibilitylib.models.CompatibilityRequest;
import com.techplanner.compatibilitylib.models.CompatibilityResult;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Minimal executable entry point for Linux, Docker, and AWS container smoke runs.
 */
public final class CompatibilityLibApplication {

    private CompatibilityLibApplication() {
    }

    public static void main(String[] args) {
        if (hasFlag(args, "--help") || hasFlag(args, "-h")) {
            printUsage();
            return;
        }

        CompatibilityResult result = new CompatibilityAnalyzer().analyze(createSampleRequest());

        System.out.println("compatibility-lib smoke run completed");
        System.out.println("Compatible: " + result.compatible());
        System.out.println("Status: " + result.status());
        System.out.println("Score: " + result.compatibilityScore());
        System.out.println("Estimated power: " + result.estimatedPowerConsumption() + " W");
        System.out.println("Recommended PSU: " + result.recommendedPsu() + " W");

        if (!result.warnings().isEmpty()) {
            System.out.println("Warnings:");
            result.warnings().forEach(warning -> System.out.println("- " + warning));
        }

        if (!result.errors().isEmpty()) {
            System.out.println("Errors:");
            result.errors().forEach(error -> System.out.println("- " + error));
        }

        if (!result.recommendations().isEmpty()) {
            System.out.println("Recommendations:");
            result.recommendations().forEach(recommendation -> System.out.println("- " + recommendation));
        }
    }

    private static boolean hasFlag(String[] args, String flag) {
        return Arrays.stream(args).anyMatch(flag::equals);
    }

    private static void printUsage() {
        System.out.println("compatibility-lib smoke runner");
        System.out.println("Usage: java -jar compatibility-lib.jar [--help]");
        System.out.println("This entry point validates that the library can execute inside Linux containers.");
    }

    private static CompatibilityRequest createSampleRequest() {
        return CompatibilityRequest.builder()
                .cpu(ComponentFactory.cpu("AMD", "Ryzen 7 7700", CpuSocket.AM5, 8, 16, 120, true, BigDecimal.valueOf(300)))
                .gpu(ComponentFactory.gpu("NVIDIA", "RTX 4070", "Ada", 12, 200, PcieVersion.PCIE_4, 240, BigDecimal.valueOf(700)))
                .ram(ComponentFactory.ram("Corsair", "Vengeance", RamType.DDR5, 32, 5600, 1.25d, 2, BigDecimal.valueOf(180)))
                .motherboard(ComponentFactory.motherboard("ASUS", "Prime", CpuSocket.AM5, RamType.DDR5, 128, 4, 6000, PcieVersion.PCIE_5, FormFactor.ATX, 4, 2, BigDecimal.valueOf(250)))
                .psu(ComponentFactory.psu("Corsair", "RM750", 750, "80+ Gold", true, FormFactor.ATX, BigDecimal.valueOf(160)))
                .storage(ComponentFactory.storage("Samsung", "990 Pro", StorageType.NVME, "M.2", 1000, 7450, 6900, BigDecimal.valueOf(140)))
                .build();
    }
}