package com.fitness.service.impl;

import com.fitness.model.Activity;
import com.fitness.model.Recommendation;
import com.fitness.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAiService activityAiService;
    private final RecommendationRepository recommendationRepository;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group")
    public void processActivity(Activity activity) {
        try{
            System.out.println("Received activity from Kafka topic for processing: "+activity.getUserId());
            Recommendation recommendation = activityAiService.generateRecommendation(activity);
            System.out.println(recommendationRepository.save(recommendation));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
