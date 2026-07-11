package com.fitness.model;

import com.fitness.model.ENUM.ActivityType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.time.LocalDateTime;


//@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "activities")
public class Activity {

    private String activityId;
    private String userId;

    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;

//    @CreationTimestamp
    @CreatedDate
    private LocalDateTime createdAt;

//    @UpdateTimestamp
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
