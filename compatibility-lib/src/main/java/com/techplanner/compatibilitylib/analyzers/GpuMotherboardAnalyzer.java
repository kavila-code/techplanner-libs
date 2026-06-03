package com.techplanner.compatibilitylib.analyzers;

import com.techplanner.compatibilitylib.models.CompatibilityRequest;
import com.techplanner.compatibilitylib.models.CompatibilityResult;
import com.techplanner.compatibilitylib.utils.CompatibilityUtils;

/**
 * Validates GPU and motherboard PCIe compatibility.
 */
public class GpuMotherboardAnalyzer {

    public void analyze(CompatibilityRequest request, CompatibilityResult.Builder resultBuilder) {
        if (request.gpu().pcieVersion() == request.motherboard().pcieVersion()) {
            resultBuilder.addRecommendation("GPU y motherboard comparten la misma versión PCIe");
            return;
        }

        if (request.gpu().pcieVersion().ordinal() > request.motherboard().pcieVersion().ordinal()) {
            resultBuilder.addWarning("GPU PCIe %s en motherboard PCIe %s es compatible pero puede reducir rendimiento"
                    .formatted(request.gpu().pcieVersion(), request.motherboard().pcieVersion()));
        } else {
            resultBuilder.addRecommendation("GPU PCIe %s es totalmente compatible con motherboard PCIe %s"
                    .formatted(request.gpu().pcieVersion(), request.motherboard().pcieVersion()));
        }
    }
}