package com.techplanner.systemrequirements.nativecode;

public class CompatibilityNative {

    public String checkCompatibility(
            int ramGb,
            int storageGb,
            boolean tpm,
            boolean secureBoot,
            String architecture,
            String cpu,
            String gpu
    ) {

        return CompatibilityLibrary.INSTANCE.checkCompatibility(
                ramGb,
                storageGb,
                tpm ? 1 : 0,
                secureBoot ? 1 : 0,
                architecture,
                cpu,
                gpu
        );
    }
}