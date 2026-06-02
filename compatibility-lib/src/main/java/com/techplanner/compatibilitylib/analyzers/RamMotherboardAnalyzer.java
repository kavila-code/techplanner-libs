package com.techplanner.compatibilitylib.analyzers;

import com.techplanner.compatibilitylib.models.CompatibilityRequest;
import com.techplanner.compatibilitylib.models.CompatibilityResult;

/**
 * Validates RAM and motherboard compatibility.
 */
public class RamMotherboardAnalyzer {

    public void analyze(CompatibilityRequest request, CompatibilityResult.Builder resultBuilder) {
        if (request.ram().type() != request.motherboard().ramType()) {
            resultBuilder.addError("RAM tipo %s no es compatible con motherboard tipo %s"
                    .formatted(request.ram().type(), request.motherboard().ramType()));
        }

        int totalRam = request.ram().capacityGb() * request.ram().sticks();
        if (totalRam > request.motherboard().maxRam()) {
            resultBuilder.addError("RAM instalada (%dGB) supera el máximo soportado por la motherboard (%dGB)"
                    .formatted(totalRam, request.motherboard().maxRam()));
        }

        if (request.ram().sticks() > request.motherboard().ramSlots()) {
            resultBuilder.addError("RAM sticks (%d) superan los slots disponibles en la motherboard (%d)"
                    .formatted(request.ram().sticks(), request.motherboard().ramSlots()));
        }

        if (request.ram().speedMHz() > request.motherboard().supportedRamSpeed()) {
            resultBuilder.addWarning("RAM velocidad %dMHz supera la velocidad soportada por la motherboard %dMHz"
                    .formatted(request.ram().speedMHz(), request.motherboard().supportedRamSpeed()));
        }
    }
}