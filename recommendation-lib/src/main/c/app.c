#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "service.h"

static void print_json_string(const char *value) {
    putchar('"');
    if (value != NULL) {
        for (const unsigned char *p = (const unsigned char *)value; *p != '\0'; ++p) {
            switch (*p) {
                case '"': fputs("\\\"", stdout); break;
                case '\\': fputs("\\\\", stdout); break;
                case '\b': fputs("\\b", stdout); break;
                case '\f': fputs("\\f", stdout); break;
                case '\n': fputs("\\n", stdout); break;
                case '\r': fputs("\\r", stdout); break;
                case '\t': fputs("\\t", stdout); break;
                default:
                    if (*p < 0x20) {
                        printf("\\u%04x", (unsigned int)*p);
                    } else {
                        putchar(*p);
                    }
                    break;
            }
        }
    }
    putchar('"');
}

static void print_result_json(const RecommendationResult *result) {
    puts("{");
    printf("  \"total\":%.2f,\n", result->estimatedTotalPrice);
    puts("  \"components\":[");

    for (size_t i = 0; i < result->components.count; ++i) {
        const ComponentRecommendation *component = result->components.items[i];
        printf("    {\"category\":");
        print_json_string(component->category);
        printf(",\"model\":");
        print_json_string(component->model);
        printf(",\"price\":%.2f}", component->price);
        if (i + 1 < result->components.count) {
            putchar(',');
        }
        putchar('\n');
    }

    puts("  ]");
    puts("}");
}

static int parse_budget(const char *value, double *budget_out) {
    char *end = NULL;
    errno = 0;
    double budget = strtod(value, &end);
    if (value == end || (end != NULL && *end != '\0') || errno == ERANGE) {
        return 0;
    }
    *budget_out = budget;
    return 1;
}

int main(int argc, char **argv) {
    if (argc != 3) {
        fprintf(stderr, "Uso: %s <usageType> <budget>\n", argv[0]);
        return 1;
    }

    double budget = 0.0;
    if (!parse_budget(argv[2], &budget)) {
        fprintf(stderr, "Error: budget must be a valid number.\n");
        return 1;
    }

    RecommendationRequest request;
    request.usageTypeStr = argv[1];
    request.budget = budget;
    request.hasBudget = 1;

    char *error = NULL;
    RecommendationResult *result = recommend(&request, &error);
    if (result == NULL) {
        fprintf(stderr, "%s\n", error != NULL ? error : "Error desconocido al generar la recomendación.");
        free(error);
        return 2;
    }

    print_result_json(result);
    result_free(result);
    free(error);
    return 0;
}