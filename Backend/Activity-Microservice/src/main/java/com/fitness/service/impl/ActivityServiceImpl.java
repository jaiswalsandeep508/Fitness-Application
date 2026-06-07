package com.fitness.service.impl;

import com.fitness.dto.request.ActivityRequest;
import com.fitness.dto.response.ActivityResponse;
import com.fitness.model.Activity;
import com.fitness.repository.ActivityRepository;
import com.fitness.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Override
    public ActivityResponse trackActivity(ActivityRequest request) {

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        return ActivityResponse.builder()
                .id(savedActivity.getActivityId())
                .userId(savedActivity.getUserId())
                .type(savedActivity.getType())
                .duration(savedActivity.getDuration())
                .caloriesBurned(savedActivity.getCaloriesBurned())
                .startTime(savedActivity.getStartTime())
                .createdAt(savedActivity.getCreatedAt())
                .updatedAt(savedActivity.getUpdatedAt())
                .build();
    }
}
