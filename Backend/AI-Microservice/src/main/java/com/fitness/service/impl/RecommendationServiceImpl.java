package com.fitness.service.impl;

import com.fitness.exception.ResourceNotFoundException;
import com.fitness.model.Recommendation;
import com.fitness.repository.RecommendationRepository;
import com.fitness.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    @Override
    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    @Override
    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Activity not found on activityId : "+activityId));
    }
}
