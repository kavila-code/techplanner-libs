package com.techplanner.compatibilitylib.analyzers;

import com.techplanner.compatibilitylib.models.CompatibilityRequest;
import com.techplanner.compatibilitylib.models.CompatibilityResult;

/**
 * Validates CPU and motherboard socket compatibility.
 */
public class CpuMotherboardAnalyzer {

    public void analyze(CompatibilityRequest request, CompatibilityResult.Builder resultBuilder) {
        if (request.cpu().socket() != request.motherboard().socket()) {
            resultBuilder.addError("CPU socket %s no es compatible con motherboard socket %s"
                    .formatted(request.cpu().socket(), request.motherboard().socket()));
            return;
        }

        resultBuilder.addRecommendation("CPU y motherboard comparten socket %s".formatted(request.cpu().socket()));
    }
}