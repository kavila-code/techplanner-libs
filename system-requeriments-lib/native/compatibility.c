#include <stdio.h>
#include <string.h>

#ifdef _WIN32
#define EXPORT __declspec(dllexport)
#else
#define EXPORT
#endif

EXPORT const char* checkCompatibility(
    int ramGb,
    int storageGb,
    int tpm,
    int secureBoot,
    const char* architecture,
    const char* cpu,
    const char* gpu
) {

    if (ramGb < 4) {
        return "NOT_COMPATIBLE: Requires 4GB RAM";
    }

    if (storageGb < 64) {
        return "NOT_COMPATIBLE: Requires 64GB storage";
    }

    if (tpm == 0) {
        return "NOT_COMPATIBLE: TPM 2.0 required";
    }

    if (secureBoot == 0) {
        return "NOT_COMPATIBLE: Secure Boot required";
    }

    if (strcmp(architecture, "x64") != 0) {
        return "NOT_COMPATIBLE: x64 required";
    }

    return "COMPATIBLE";
}