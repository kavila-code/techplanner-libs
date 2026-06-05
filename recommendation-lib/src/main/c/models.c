#include "models.h"
#include <stdlib.h>
#include <string.h>

char *strdup_safe(const char *s) {
    if (!s) return NULL;
    size_t len = strlen(s);
    char *d = malloc(len + 1);
    if (!d) return NULL;
    memcpy(d, s, len + 1);
    return d;
}

ComponentRecommendation *component_new(const char *category,
                                       const char *model,
                                       double price,
                                       const char *socket,
                                       const char *ramType,
                                       int capacityGb,
                                       int powerConsumptionWatts,
                                       int psuWattage,
                                       int maxRamGb,
                                       const char *storageInterface) {
    ComponentRecommendation *c = malloc(sizeof(ComponentRecommendation));
    if (!c) return NULL;
    c->category = strdup_safe(category);
    c->model = strdup_safe(model);
    c->price = price;
    c->socket = strdup_safe(socket);
    c->ramType = strdup_safe(ramType);
    c->capacityGb = capacityGb;
    c->powerConsumptionWatts = powerConsumptionWatts;
    c->psuWattage = psuWattage;
    c->maxRamGb = maxRamGb;
    c->storageInterface = strdup_safe(storageInterface);
    return c;
}

static void component_free(ComponentRecommendation *c) {
    if (!c) return;
    free(c->category);
    free(c->model);
    free(c->socket);
    free(c->ramType);
    free(c->storageInterface);
    free(c);
}

void component_list_add(ComponentList *list, ComponentRecommendation *item) {
    list->items = realloc(list->items, sizeof(ComponentRecommendation *) * (list->count + 1));
    list->items[list->count++] = item;
}

void component_list_free(ComponentList *list) {
    if (!list) return;
    for (size_t i = 0; i < list->count; ++i) {
        component_free(list->items[i]);
    }
    free(list->items);
    list->items = NULL;
    list->count = 0;
}