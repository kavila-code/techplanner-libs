#ifndef SERVICE_H
#define SERVICE_H

#include "models.h"

UsageType usage_from_string(const char *value);
RecommendationResult *recommend(const RecommendationRequest *request, char **out_error);
void result_free(RecommendationResult *r);

#endif // SERVICE_H