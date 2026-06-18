package com.fitness.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recommendation")
public class Recommendation {

    @Id
    private String id;
    private String activityId;
    private String userId;
    private String recommendation;
    private List<String> suggestion;
    private List<String> safety;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
