package com.fitness.service;

import com.fitness.dto.request.UserRequest;
import com.fitness.dto.response.UserResponse;


public interface UserService {
    UserResponse register(UserRequest request);
    UserResponse getUserProfile(String userId);
}
