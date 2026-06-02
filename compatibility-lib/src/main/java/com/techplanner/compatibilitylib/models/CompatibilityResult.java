package com.techplanner.compatibilitylib.models;

import com.techplanner.compatibilitylib.enums.CompatibilityStatus;
import com.techplanner.compatibilitylib.utils.CompatibilityUtils;

import java.util.List;

/**
 * Final compatibility analysis result.
 */
public record CompatibilityResult(
        boolean compatible,
        CompatibilityStatus status,
        int compatibilityScore,
        List<String> warnings,
        List<String> errors,
        List<String> recommendations,
        int estimatedPowerConsumption,
        int recommendedPsu
) {

    public CompatibilityResult {
        warnings = CompatibilityUtils.immutableList(warnings);
        errors = CompatibilityUtils.immutableList(errors);
        recommendations = CompatibilityUtils.immutableList(recommendations);
        status = java.util.Objects.requireNonNull(status, "status");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private boolean compatible;
        private CompatibilityStatus status = CompatibilityStatus.PERFECT;
        private int compatibilityScore;
        private final java.util.List<String> warnings = new java.util.ArrayList<>();
        private final java.util.List<String> errors = new java.util.ArrayList<>();
        private final java.util.List<String> recommendations = new java.util.ArrayList<>();
        private int estimatedPowerConsumption;
        private int recommendedPsu;

        private Builder() {
        }

        public Builder compatible(boolean compatible) {
            this.compatible = compatible;
            return this;
        }

        public Builder status(CompatibilityStatus status) {
            this.status = status;
            return this;
        }

        public Builder compatibilityScore(int compatibilityScore) {
            this.compatibilityScore = compatibilityScore;
            return this;
        }

        public Builder addWarning(String warning) {
            this.warnings.add(warning);
            return this;
        }

        public Builder addError(String error) {
            this.errors.add(error);
            return this;
        }

        public Builder addRecommendation(String recommendation) {
            this.recommendations.add(recommendation);
            return this;
        }

        public java.util.List<String> warnings() {
            return java.util.List.copyOf(warnings);
        }

        public java.util.List<String> errors() {
            return java.util.List.copyOf(errors);
        }

        public java.util.List<String> recommendations() {
            return java.util.List.copyOf(recommendations);
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }

        public boolean hasWarnings() {
            return !warnings.isEmpty();
        }

        public int warningCount() {
            return warnings.size();
        }

        public int errorCount() {
            return errors.size();
        }

        public int estimatedPowerConsumption() {
            return estimatedPowerConsumption;
        }

        public int recommendedPsu() {
            return recommendedPsu;
        }

        public Builder warnings(List<String> warnings) {
            this.warnings.clear();
            this.warnings.addAll(CompatibilityUtils.immutableList(warnings));
            return this;
        }

        public Builder errors(List<String> errors) {
            this.errors.clear();
            this.errors.addAll(CompatibilityUtils.immutableList(errors));
            return this;
        }

        public Builder recommendations(List<String> recommendations) {
            this.recommendations.clear();
            this.recommendations.addAll(CompatibilityUtils.immutableList(recommendations));
            return this;
        }

        public Builder estimatedPowerConsumption(int estimatedPowerConsumption) {
            this.estimatedPowerConsumption = estimatedPowerConsumption;
            return this;
        }

        public Builder recommendedPsu(int recommendedPsu) {
            this.recommendedPsu = recommendedPsu;
            return this;
        }

        public CompatibilityResult build() {
            return new CompatibilityResult(compatible, status, compatibilityScore, warnings, errors, recommendations, estimatedPowerConsumption, recommendedPsu);
        }
    }
}