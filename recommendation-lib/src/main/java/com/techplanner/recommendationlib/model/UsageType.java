package com.techplanner.recommendationlib.model;

import java.text.Normalizer;
import java.util.Locale;

public enum UsageType {
    GAMING,
    OFFICE,
    DESIGN,
    SERVERS,
    BUDGET

    ;

    public static UsageType from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El tipo de uso es obligatorio.");
        }

        String normalized = normalize(value);

        return switch (normalized) {
            case "gaming" -> GAMING;
            case "oficina", "office" -> OFFICE;
            case "diseno", "design" -> DESIGN;
            case "servidores", "server", "servers" -> SERVERS;
            case "presupuesto", "budget", "barato" -> BUDGET;
            default -> OFFICE;
        };
    }

    private static String normalize(String value) {
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .trim()
                .toLowerCase(Locale.ROOT);
        return normalized;
    }
}
