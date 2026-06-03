#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "models.h"
#include "service.h"
#include "validator.h"

int contains_note(RecommendationResult *r, const char *needle) {
    for (size_t i = 0; i < r->notesCount; ++i) {
        if (strstr(r->notes[i], needle) != NULL) return 1;
    }
    return 0;
}

int main(void) {
    printf("Running C port tests...\n");

    RecommendationRequest req1 = {"gaming", 3000.0, 1};
    char *err = NULL;
    RecommendationResult *res1 = recommend(&req1, &err);
    if (!res1) {
        fprintf(stderr, "Test1 failed: recommend returned NULL: %s\n", err ? err : "no error");
        free(err);
        return 2;
    }

    int ok = 1;
    if (res1->components.count != 7) {
        fprintf(stderr, "Test1 failed: expected 7 components, got %zu\n", res1->components.count);
        ok = 0;
    }
    if (res1->usageType != USAGE_GAMING) {
        fprintf(stderr, "Test1 failed: expected usage GAMING\n");
        ok = 0;
    }
    if (fabs(res1->estimatedTotalPrice - 1570.0) > 0.001) {
        fprintf(stderr, "Test1 failed: expected total 1570.0, got %.2f\n", res1->estimatedTotalPrice);
        ok = 0;
    }
    if (!contains_note(res1, "Configuración dentro del presupuesto estimado.")) {
        fprintf(stderr, "Test1 failed: missing budget note\n");
        ok = 0;
    }

    result_free(res1);
    if (!ok) return 3;

    RecommendationRequest req2 = {"office", -1.0, 1};
    RecommendationResult *res2 = recommend(&req2, &err);
    if (res2 != NULL) {
        fprintf(stderr, "Test2 failed: expected NULL due to negative budget\n");
        result_free(res2);
        return 4;
    }
    if (err == NULL || strstr(err, "presupuesto no puede ser negativo") == NULL) {
        fprintf(stderr, "Test2 failed: expected error message about negative budget, got: %s\n", err ? err : "(null)");
        free(err);
        return 5;
    }

    free(err);
    printf("All tests passed.\n");
    return 0;
}