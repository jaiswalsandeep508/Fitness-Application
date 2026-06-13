package com.fitness.service.impl;


import com.fitness.dto.request.ActivityRequest;
import com.fitness.dto.response.ActivityResponse;
import com.fitness.exception.ResourceNotFoundException;
import com.fitness.model.Activity;
import com.fitness.repository.ActivityRepository;
import com.fitness.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final WebClient userServiceWebClient;

    @Override
    public ActivityResponse trackActivity(ActivityRequest request) {

        if(!isUserExist(request.getUserId())){
            throw new ResourceNotFoundException(
                    "User not exist with userId: " + request.getUserId()
            );
        }

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

//    -------------------Helper method-------------------
    private boolean isUserExist(String userId) {
        return Boolean.TRUE.equals(userServiceWebClient.get()
                .uri("/api/users/{userId}/validate",userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }
}
