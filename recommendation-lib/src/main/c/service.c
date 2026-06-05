#include "service.h"
#include "validator.h"
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

static char *str_tolower(const char *s) {
    if (!s) return NULL;
    char *d = strdup_safe(s);
    if (!d) return NULL;
    for (char *p = d; *p; ++p) *p = (char) tolower((unsigned char)*p);
    return d;
}

UsageType usage_from_string(const char *value) {
    if (value == NULL) return USAGE_UNKNOWN;
    char *norm = str_tolower(value);
    UsageType t = USAGE_OFFICE;
    if (strcmp(norm, "gaming") == 0) t = USAGE_GAMING;
    else if (strcmp(norm, "oficina") == 0 || strcmp(norm, "office") == 0) t = USAGE_OFFICE;
    else if (strcmp(norm, "diseno") == 0 || strcmp(norm, "design") == 0) t = USAGE_DESIGN;
    else if (strcmp(norm, "workstation") == 0) t = USAGE_WORKSTATION;
    else if (strcmp(norm, "servidores") == 0 || strcmp(norm, "server") == 0 || strcmp(norm, "servers") == 0) t = USAGE_SERVERS;
    else if (strcmp(norm, "presupuesto") == 0 || strcmp(norm, "budget") == 0 || strcmp(norm, "barato") == 0) t = USAGE_BUDGET;
    free(norm);
    return t;
}

static void add_note(RecommendationResult *r, const char *note) {
    r->notes = realloc(r->notes, sizeof(char *) * (r->notesCount + 1));
    r->notes[r->notesCount++] = strdup_safe(note);
}

static void add_component(RecommendationResult *r, ComponentRecommendation *c) {
    component_list_add(&r->components, c);
}

static void gaming(RecommendationResult *r) {
    add_component(r, component_new("Motherboard", "B650 ATX", 220.0, NULL, NULL, 0, 40, 0, 192, NULL));
    add_component(r, component_new("CPU", "AMD Ryzen 7 7800X", 320.0, "AM5", NULL, 0, 105, 0, 0, NULL));
    add_component(r, component_new("GPU", "NVIDIA RTX 4070", 600.0, NULL, NULL, 0, 200, 0, 0, NULL));
    add_component(r, component_new("RAM", "32GB DDR5", 120.0, NULL, "DDR5", 32, 10, 0, 0, NULL));
    add_component(r, component_new("Storage", "1TB NVMe", 80.0, NULL, NULL, 1000, 5, 0, 0, "NVMe"));
    add_component(r, component_new("PSU", "750W Gold", 110.0, NULL, NULL, 0, 0, 750, 0, NULL));
    add_component(r, component_new("OS", "Windows 11 Home", 120.0, NULL, NULL, 0, 0, 0, 0, NULL));
}

static void office(RecommendationResult *r) {
    add_component(r, component_new("Motherboard", "H610 mATX", 110.0, NULL, NULL, 0, 25, 0, 64, NULL));
    add_component(r, component_new("CPU", "Intel Core i3 13100", 120.0, "LGA1700", NULL, 0, 60, 0, 0, NULL));
    add_component(r, component_new("GPU", "Integrated", 0.0, NULL, NULL, 0, 0, 0, 0, NULL));
    add_component(r, component_new("RAM", "16GB DDR4", 50.0, NULL, "DDR4", 16, 8, 0, 0, NULL));
    add_component(r, component_new("Storage", "512GB SSD", 40.0, NULL, NULL, 512, 5, 0, 0, "SATA"));
    add_component(r, component_new("PSU", "450W Bronze", 60.0, NULL, NULL, 0, 0, 450, 0, NULL));
    add_component(r, component_new("OS", "Windows 11 Pro", 140.0, NULL, NULL, 0, 0, 0, 0, NULL));
}

static void design(RecommendationResult *r) {
    add_component(r, component_new("Motherboard", "Z790 ATX", 260.0, NULL, NULL, 0, 35, 0, 192, NULL));
    add_component(r, component_new("CPU", "Intel Core i9 13900K", 560.0, "LGA1700", NULL, 0, 125, 0, 0, NULL));
    add_component(r, component_new("GPU", "NVIDIA RTX 4080", 1200.0, NULL, NULL, 0, 320, 0, 0, NULL));
    add_component(r, component_new("RAM", "64GB DDR5", 280.0, NULL, "DDR5", 64, 18, 0, 0, NULL));
    add_component(r, component_new("Storage", "2TB NVMe", 220.0, NULL, NULL, 2000, 8, 0, 0, "NVMe"));
    add_component(r, component_new("PSU", "1000W Gold", 180.0, NULL, NULL, 0, 0, 1000, 0, NULL));
    add_component(r, component_new("OS", "Windows 11 Pro", 140.0, NULL, NULL, 0, 0, 0, 0, NULL));
}

static void workstation(RecommendationResult *r) {
    add_component(r, component_new("Motherboard", "B760 Pro ATX", 180.0, NULL, NULL, 0, 30, 0, 192, NULL));
    add_component(r, component_new("CPU", "Intel Core i7 13700", 360.0, "LGA1700", NULL, 0, 125, 0, 0, NULL));
    add_component(r, component_new("GPU", "NVIDIA RTX 4060", 350.0, NULL, NULL, 0, 160, 0, 0, NULL));
    add_component(r, component_new("RAM", "32GB DDR5", 140.0, NULL, "DDR5", 32, 12, 0, 0, NULL));
    add_component(r, component_new("Storage", "1TB NVMe", 90.0, NULL, NULL, 1000, 5, 0, 0, "NVMe"));
    add_component(r, component_new("PSU", "750W Gold", 110.0, NULL, NULL, 0, 0, 750, 0, NULL));
    add_component(r, component_new("OS", "Windows 11 Pro", 140.0, NULL, NULL, 0, 0, 0, 0, NULL));
}

static void servers(RecommendationResult *r) {
    add_component(r, component_new("Motherboard", "SP5 Server Board", 850.0, NULL, NULL, 0, 60, 0, 1024, NULL));
    add_component(r, component_new("CPU", "AMD EPYC (multi-socket)", 2000.0, "SP5", NULL, 0, 280, 0, 0, NULL));
    add_component(r, component_new("GPU", "None / Optional", 0.0, NULL, NULL, 0, 0, 0, 0, NULL));
    add_component(r, component_new("RAM", "128GB ECC", 800.0, NULL, "DDR5 ECC", 128, 30, 0, 0, NULL));
    add_component(r, component_new("Storage", "4TB SATA RAID", 400.0, NULL, NULL, 4000, 20, 0, 0, "SATA"));
    add_component(r, component_new("PSU", "1200W Platinum", 250.0, NULL, NULL, 0, 0, 1200, 0, NULL));
    add_component(r, component_new("OS", "Linux (Ubuntu Server)", 0.0, NULL, NULL, 0, 0, 0, 0, NULL));
}

static void budgetBuild(RecommendationResult *r) {
    add_component(r, component_new("Motherboard", "H610 Basic", 90.0, NULL, NULL, 0, 20, 0, 64, NULL));
    add_component(r, component_new("CPU", "Intel Pentium Gold", 70.0, "LGA1700", NULL, 0, 46, 0, 0, NULL));
    add_component(r, component_new("GPU", "Integrated", 0.0, NULL, NULL, 0, 0, 0, 0, NULL));
    add_component(r, component_new("RAM", "8GB DDR4", 25.0, NULL, "DDR4", 8, 5, 0, 0, NULL));
    add_component(r, component_new("Storage", "256GB SSD", 25.0, NULL, NULL, 256, 3, 0, 0, "SATA"));
    add_component(r, component_new("PSU", "400W Bronze", 50.0, NULL, NULL, 0, 0, 400, 0, NULL));
    add_component(r, component_new("OS", "Linux (Ubuntu)", 0.0, NULL, NULL, 0, 0, 0, 0, NULL));
}

RecommendationResult *recommend(const RecommendationRequest *request, char **out_error) {
    if (out_error) *out_error = NULL;
    if (!validate_request(request, out_error)) return NULL;

    RecommendationResult *r = malloc(sizeof(RecommendationResult));
    r->components.items = NULL;
    r->components.count = 0;
    r->estimatedTotalPrice = 0.0;
    r->notes = NULL;
    r->notesCount = 0;

    r->usageType = usage_from_string(request->usageTypeStr);
    add_note(r, "Configuración para:");

    switch (r->usageType) {
        case USAGE_GAMING: gaming(r); break;
        case USAGE_OFFICE: office(r); break;
        case USAGE_DESIGN: design(r); break;
        case USAGE_WORKSTATION: workstation(r); break;
        case USAGE_SERVERS: servers(r); break;
        case USAGE_BUDGET: budgetBuild(r); break;
        default: office(r); break;
    }

    double total = 0.0;
    for (size_t i = 0; i < r->components.count; ++i) {
        total += r->components.items[i]->price;
    }
    r->estimatedTotalPrice = total;

    if (request->hasBudget) {
        if (r->estimatedTotalPrice > request->budget) {
            add_note(r, "La configuración recomendada excede el presupuesto.");
        } else {
            add_note(r, "Configuración dentro del presupuesto estimado.");
        }
    }

    switch (r->usageType) {
        case USAGE_GAMING: add_note(r, "Alto rendimiento gaming"); break;
        case USAGE_OFFICE: add_note(r, "Uso básico oficina"); break;
        case USAGE_DESIGN: add_note(r, "Diseño profesional"); break;
        case USAGE_WORKSTATION: add_note(r, "Productividad avanzada y multitarea"); break;
        case USAGE_SERVERS: add_note(r, "Servidor / enterprise"); break;
        case USAGE_BUDGET: add_note(r, "Económico"); break;
        default: break;
    }

    return r;
}

void result_free(RecommendationResult *r) {
    if (!r) return;
    component_list_free(&r->components);
    for (size_t i = 0; i < r->notesCount; ++i) free(r->notes[i]);
    free(r->notes);
    free(r);
}