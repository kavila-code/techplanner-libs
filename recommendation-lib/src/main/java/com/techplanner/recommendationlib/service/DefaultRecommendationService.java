package com.techplanner.recommendationlib.service;

import com.techplanner.recommendationlib.model.ComponentRecommendation;
import com.techplanner.recommendationlib.model.RecommendationRequest;
import com.techplanner.recommendationlib.model.RecommendationResult;
import com.techplanner.recommendationlib.model.UsageType;
import com.techplanner.recommendationlib.validation.RecommendationValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DefaultRecommendationService implements RecommendationService {

    @Override
    public RecommendationResult recommend(RecommendationRequest request) {
        RecommendationValidator.validate(request);

        UsageType usageType = UsageType.from(request.usageType());
        List<ComponentRecommendation> components = new ArrayList<>();

        switch (usageType) {
            case GAMING -> gaming(components);
            case OFFICE -> office(components);
            case DESIGN -> design(components);
            case SERVERS -> servers(components);
            case BUDGET -> budgetBuild(components);
        }

        BigDecimal estimatedTotal = components.stream()
                .map(ComponentRecommendation::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<String> notes = new ArrayList<>();
        if (request.budget() != null) {
            if (estimatedTotal.compareTo(request.budget()) > 0) {
                notes.add("La configuración recomendada excede el presupuesto.");
            } else {
                notes.add("Configuración dentro del presupuesto estimado.");
            }
        }

        return new RecommendationResult(usageType, List.copyOf(components), estimatedTotal, List.copyOf(notes));
    }

    private void gaming(List<ComponentRecommendation> out) {
        out.add(new ComponentRecommendation("Motherboard", "B650 ATX", new BigDecimal("220"), null, null, null, 40, null, 192, null, List.of("AM5"), List.of("DDR5"), List.of("NVMe", "SATA")));
        out.add(new ComponentRecommendation("CPU", "AMD Ryzen 7 7800X", new BigDecimal("320"), "AM5", null, null, 105, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("GPU", "NVIDIA RTX 4070", new BigDecimal("600"), null, null, null, 200, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("RAM", "32GB DDR5", new BigDecimal("120"), null, "DDR5", 32, 10, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("Storage", "1TB NVMe", new BigDecimal("80"), null, null, 1000, 5, null, null, "NVMe", null, null, null));
        out.add(new ComponentRecommendation("PSU", "750W Gold", new BigDecimal("110"), null, null, null, null, 750, null, null, null, null, null));
        out.add(new ComponentRecommendation("OS", "Windows 11 Home", new BigDecimal("120"), null, null, null, 0, null, null, null, null, null, null));
    }

    private void office(List<ComponentRecommendation> out) {
        out.add(new ComponentRecommendation("Motherboard", "H610 mATX", new BigDecimal("110"), null, null, null, 25, null, 64, null, List.of("LGA1700"), List.of("DDR4"), List.of("SATA", "NVMe")));
        out.add(new ComponentRecommendation("CPU", "Intel Core i3 13100", new BigDecimal("120"), "LGA1700", null, null, 60, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("GPU", "Integrated", new BigDecimal("0"), null, null, null, 0, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("RAM", "16GB DDR4", new BigDecimal("50"), null, "DDR4", 16, 8, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("Storage", "512GB SSD", new BigDecimal("40"), null, null, 512, 5, null, null, "SATA", null, null, null));
        out.add(new ComponentRecommendation("PSU", "450W Bronze", new BigDecimal("60"), null, null, null, null, 450, null, null, null, null, null));
        out.add(new ComponentRecommendation("OS", "Windows 11 Pro", new BigDecimal("140"), null, null, null, 0, null, null, null, null, null, null));
    }

    private void design(List<ComponentRecommendation> out) {
        out.add(new ComponentRecommendation("Motherboard", "Z790 ATX", new BigDecimal("260"), null, null, null, 35, null, 192, null, List.of("LGA1700"), List.of("DDR5"), List.of("NVMe", "SATA")));
        out.add(new ComponentRecommendation("CPU", "Intel Core i9 13900K", new BigDecimal("560"), "LGA1700", null, null, 125, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("GPU", "NVIDIA RTX 4080", new BigDecimal("1200"), null, null, null, 320, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("RAM", "64GB DDR5", new BigDecimal("280"), null, "DDR5", 64, 18, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("Storage", "2TB NVMe", new BigDecimal("220"), null, null, 2000, 8, null, null, "NVMe", null, null, null));
        out.add(new ComponentRecommendation("PSU", "1000W Gold", new BigDecimal("180"), null, null, null, null, 1000, null, null, null, null, null));
        out.add(new ComponentRecommendation("OS", "Windows 11 Pro", new BigDecimal("140"), null, null, null, 0, null, null, null, null, null, null));
    }

    private void servers(List<ComponentRecommendation> out) {
        out.add(new ComponentRecommendation("Motherboard", "SP5 Server Board", new BigDecimal("850"), null, null, null, 60, null, 1024, null, List.of("SP5"), List.of("DDR5 ECC"), List.of("NVMe", "SATA")));
        out.add(new ComponentRecommendation("CPU", "AMD EPYC (multi-socket)", new BigDecimal("2000"), "SP5", null, null, 280, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("GPU", "None / Optional", new BigDecimal("0"), null, null, null, 0, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("RAM", "128GB ECC", new BigDecimal("800"), null, "DDR5 ECC", 128, 30, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("Storage", "4TB SATA RAID", new BigDecimal("400"), null, null, 4000, 20, null, null, "SATA", null, null, null));
        out.add(new ComponentRecommendation("PSU", "1200W Platinum", new BigDecimal("250"), null, null, null, null, 1200, null, null, null, null, null));
        out.add(new ComponentRecommendation("OS", "Linux (Ubuntu Server)", new BigDecimal("0"), null, null, null, 0, null, null, null, null, null, null));
    }

    private void budgetBuild(List<ComponentRecommendation> out) {
        out.add(new ComponentRecommendation("Motherboard", "H610 Basic", new BigDecimal("90"), null, null, null, 20, null, 64, null, List.of("LGA1700"), List.of("DDR4"), List.of("SATA", "NVMe")));
        out.add(new ComponentRecommendation("CPU", "Intel Pentium Gold", new BigDecimal("70"), "LGA1700", null, null, 46, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("GPU", "Integrated", new BigDecimal("0"), null, null, null, 0, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("RAM", "8GB DDR4", new BigDecimal("25"), null, "DDR4", 8, 5, null, null, null, null, null, null));
        out.add(new ComponentRecommendation("Storage", "256GB SSD", new BigDecimal("25"), null, null, 256, 3, null, null, "SATA", null, null, null));
        out.add(new ComponentRecommendation("PSU", "400W Bronze", new BigDecimal("50"), null, null, null, null, 400, null, null, null, null, null));
        out.add(new ComponentRecommendation("OS", "Linux (Ubuntu)", new BigDecimal("0"), null, null, null, 0, null, null, null, null, null, null));
    }
}
