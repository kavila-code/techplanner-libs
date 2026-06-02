package com.techplanner.compatibilitylib.analyzers;

import com.techplanner.compatibilitylib.calculators.CompatibilityScoreCalculator;
import com.techplanner.compatibilitylib.enums.CompatibilityStatus;
import com.techplanner.compatibilitylib.models.CompatibilityRequest;
import com.techplanner.compatibilitylib.models.CompatibilityResult;

import java.util.List;

/**
 * Main facade that orchestrates all component compatibility checks.
 */
public class CompatibilityAnalyzer {

    private final CpuMotherboardAnalyzer cpuMotherboardAnalyzer;
    private final RamMotherboardAnalyzer ramMotherboardAnalyzer;
    private final GpuMotherboardAnalyzer gpuMotherboardAnalyzer;
    private final PsuAnalyzer psuAnalyzer;
    private final StorageMotherboardAnalyzer storageMotherboardAnalyzer;
    private final CompatibilityScoreCalculator compatibilityScoreCalculator;

    public CompatibilityAnalyzer() {
        this(new CpuMotherboardAnalyzer(), new RamMotherboardAnalyzer(), new GpuMotherboardAnalyzer(), new PsuAnalyzer(), new StorageMotherboardAnalyzer(), new CompatibilityScoreCalculator());
    }

    public CompatibilityAnalyzer(
            CpuMotherboardAnalyzer cpuMotherboardAnalyzer,
            RamMotherboardAnalyzer ramMotherboardAnalyzer,
            GpuMotherboardAnalyzer gpuMotherboardAnalyzer,
            PsuAnalyzer psuAnalyzer,
            StorageMotherboardAnalyzer storageMotherboardAnalyzer,
            CompatibilityScoreCalculator compatibilityScoreCalculator) {
        this.cpuMotherboardAnalyzer = cpuMotherboardAnalyzer;
        this.ramMotherboardAnalyzer = ramMotherboardAnalyzer;
        this.gpuMotherboardAnalyzer = gpuMotherboardAnalyzer;
        this.psuAnalyzer = psuAnalyzer;
        this.storageMotherboardAnalyzer = storageMotherboardAnalyzer;
        this.compatibilityScoreCalculator = compatibilityScoreCalculator;
    }

    public CompatibilityResult analyze(CompatibilityRequest request) {
        CompatibilityResult.Builder resultBuilder = CompatibilityResult.builder();

        cpuMotherboardAnalyzer.analyze(request, resultBuilder);
        ramMotherboardAnalyzer.analyze(request, resultBuilder);
        gpuMotherboardAnalyzer.analyze(request, resultBuilder);
        psuAnalyzer.analyze(request, resultBuilder);
        storageMotherboardAnalyzer.analyze(request, resultBuilder);

        CompatibilityStatus status = determineStatus(resultBuilder);
        int score = compatibilityScoreCalculator.calculateScore(resultBuilder.warningCount(), resultBuilder.errorCount(), status);

        resultBuilder
                .compatible(resultBuilder.errorCount() == 0)
                .status(status)
                .compatibilityScore(score);

        return resultBuilder.build();
    }

    private CompatibilityStatus determineStatus(CompatibilityResult.Builder resultBuilder) {
        if (resultBuilder.errorCount() > 0) {
            return CompatibilityStatus.INCOMPATIBLE;
        }

        if (resultBuilder.warningCount() == 0) {
            return CompatibilityStatus.PERFECT;
        }

        int provisionalScore = 100 - (resultBuilder.warningCount() * 8);
        if (provisionalScore >= 80) {
            return CompatibilityStatus.COMPATIBLE;
        }

        if (provisionalScore >= 50) {
            return CompatibilityStatus.PARTIALLY_COMPATIBLE;
        }

        return CompatibilityStatus.INCOMPATIBLE;
    }

    public List<String> warnings(CompatibilityRequest request) {
        return analyze(request).warnings();
    }
}