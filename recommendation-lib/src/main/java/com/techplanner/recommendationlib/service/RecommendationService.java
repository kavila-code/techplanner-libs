package com.techplanner.recommendationlib.service;

import com.techplanner.recommendationlib.model.RecommendationRequest;
import com.techplanner.recommendationlib.model.RecommendationResult;

public interface RecommendationService {
    RecommendationResult recommend(RecommendationRequest request);
}
