package com.fitness.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

//@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "recommendation")
@Document(collection = "recommendation")
@Data
@Builder
public class Recommendation {

    @Id
    private String id;
    private String activityId;
    private String userId;
    private String recommendation;
    private List<String> improvements;
    private List<String> suggestion;
    private List<String> safety;

    @CreatedDate
    private LocalDateTime createdAt;

}
