package com.fitness.controller;

import com.fitness.dto.request.ActivityRequest;
import com.fitness.dto.response.ActivityResponse;
import com.fitness.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse>trackActivity(@RequestBody ActivityRequest request){
        return new ResponseEntity<>(activityService.trackActivity(request), HttpStatus.CREATED);
    }

}
