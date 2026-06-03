#ifndef MODELS_H
#define MODELS_H

#include <stddef.h>

typedef enum {
    USAGE_GAMING,
    USAGE_OFFICE,
    USAGE_DESIGN,
    USAGE_WORKSTATION,
    USAGE_SERVERS,
    USAGE_BUDGET,
    USAGE_UNKNOWN
} UsageType;

typedef struct {
    char *category;
    char *model;
    double price;
    char *socket;
    char *ramType;
    int capacityGb;
    int powerConsumptionWatts;
    int psuWattage;
    int maxRamGb;
    char *storageInterface;
} ComponentRecommendation;

typedef struct {
    char *usageTypeStr;
    double budget;
    int hasBudget;
} RecommendationRequest;

typedef struct {
    ComponentRecommendation **items;
    size_t count;
} ComponentList;

typedef struct {
    UsageType usageType;
    ComponentList components;
    double estimatedTotalPrice;
    char **notes;
    size_t notesCount;
} RecommendationResult;

char *strdup_safe(const char *s);

ComponentRecommendation *component_new(
    const char *category,
    const char *model,
    double price,
    const char *socket,
    const char *ramType,
    int ramSizeGB,
    int powerConsumptionWatts,
    int psuWatts,
    int storageGB,
    const char *notes
);

void component_list_add(ComponentList *list, ComponentRecommendation *component);

void component_list_free(ComponentList *list);

#endif // MODELS_H