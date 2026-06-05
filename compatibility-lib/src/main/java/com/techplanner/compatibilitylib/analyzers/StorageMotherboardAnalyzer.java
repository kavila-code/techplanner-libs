package com.techplanner.compatibilitylib.analyzers;

import com.techplanner.compatibilitylib.enums.StorageType;
import com.techplanner.compatibilitylib.models.CompatibilityRequest;
import com.techplanner.compatibilitylib.models.CompatibilityResult;
import com.techplanner.compatibilitylib.utils.CompatibilityUtils;

/**
 * Validates storage and motherboard compatibility.
 */
public class StorageMotherboardAnalyzer {

    public void analyze(CompatibilityRequest request, CompatibilityResult.Builder resultBuilder) {
        StorageType storageType = request.storage().type();
        String interfaceType = CompatibilityUtils.normalizeText(request.storage().interfaceType());

        switch (storageType) {
            case SATA -> validateSataStorage(request, resultBuilder, interfaceType);
            case NVME -> validateNvmeStorage(request, resultBuilder, interfaceType);
            case SSD -> validateSsdStorage(request, resultBuilder, interfaceType);
        }
    }

    private void validateSataStorage(CompatibilityRequest request, CompatibilityResult.Builder resultBuilder, String interfaceType) {
        if (!CompatibilityUtils.containsAny(interfaceType, "SATA")) {
            resultBuilder.addError("Storage SATA requiere una interfaz SATA válida");
            return;
        }

        if (request.motherboard().sataPorts() <= 0) {
            resultBuilder.addError("Storage SATA no es compatible porque la motherboard no tiene puertos SATA disponibles");
            return;
        }

        resultBuilder.addRecommendation("Storage SATA compatible con la motherboard");
    }

    private void validateNvmeStorage(CompatibilityRequest request, CompatibilityResult.Builder resultBuilder, String interfaceType) {
        if (!CompatibilityUtils.containsAny(interfaceType, "NVME", "M.2")) {
            resultBuilder.addError("Storage NVMe requiere interfaz NVMe o M.2");
            return;
        }

        if (request.motherboard().m2Slots() <= 0) {
            resultBuilder.addError("Storage NVMe no es compatible porque la motherboard no tiene slots M.2 disponibles");
            return;
        }

        resultBuilder.addRecommendation("Storage NVMe compatible con la motherboard");
    }

    private void validateSsdStorage(CompatibilityRequest request, CompatibilityResult.Builder resultBuilder, String interfaceType) {
        if (CompatibilityUtils.containsAny(interfaceType, "NVME", "M.2")) {
            validateNvmeStorage(request, resultBuilder, interfaceType);
            return;
        }

        if (CompatibilityUtils.containsAny(interfaceType, "SATA")) {
            validateSataStorage(request, resultBuilder, interfaceType);
            return;
        }

        resultBuilder.addError("Storage SSD requiere una interfaz SATA o NVMe compatible");
    }
}