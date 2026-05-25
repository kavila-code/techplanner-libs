package com.techplanner.systemrequirements.nativecode;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface CompatibilityLibrary extends Library {

    CompatibilityLibrary INSTANCE =
            Native.load(
                    "C:\\Users\\juanf\\IdeaProjects\\techplanner-libs\\system-requirements-lib\\native\\compatibility.dll",
                    CompatibilityLibrary.class
            );

    String checkCompatibility(
            int ramGb,
            int storageGb,
            int tpm,
            int secureBoot,
            String architecture,
            String cpu,
            String gpu
    );
}