#include "validator.h"
#include <string.h>
#include <stdlib.h>

int validate_request(const RecommendationRequest *request, char **out_error) {
    if (out_error) *out_error = NULL;
    if (request == NULL) {
        if (out_error) *out_error = strdup_safe("La solicitud de recomendación no puede ser nula.");
        return 0;
    }
    if (request->usageTypeStr == NULL || strlen(request->usageTypeStr) == 0) {
        if (out_error) *out_error = strdup_safe("El tipo de uso es obligatorio.");
        return 0;
    }
    if (request->hasBudget && request->budget < 0) {
        if (out_error) *out_error = strdup_safe("El presupuesto no puede ser negativo.");
        return 0;
    }
    return 1;
}