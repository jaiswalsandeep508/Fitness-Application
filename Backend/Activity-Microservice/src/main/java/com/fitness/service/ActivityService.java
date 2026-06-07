package com.fitness.service;

import com.fitness.dto.request.ActivityRequest;
import com.fitness.dto.response.ActivityResponse;

public interface ActivityService {
    ActivityResponse trackActivity(ActivityRequest request);
}
