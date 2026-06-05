#ifndef VALIDATOR_H
#define VALIDATOR_H

#include "models.h"

int validate_request(const RecommendationRequest *request, char **out_error);

#endif // VALIDATOR_H