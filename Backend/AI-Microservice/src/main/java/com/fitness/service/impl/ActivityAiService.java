package com.fitness.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.model.Activity;
import com.fitness.model.Recommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityAiService {

    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        System.out.println("Prompt: " + prompt);
        String aiResponse = geminiService.getAnswer(prompt);
        System.out.println("AI Response: " + aiResponse);
        Recommendation recommendation = processAiResponse(activity,aiResponse);
        System.out.println("Recommendation: " + recommendation.getRecommendation());
        return recommendation;
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
                Analyze this fitness activity and provide detailed recommendation in the following EXACT JSON FORMAT :
                {
                    "analysis":{
                        "overall": "Overall analysis here",
                        "pace": "Pace analysis here",
                        "heartRate": "Heart rate analysis here".
                        "calories": "Calories analysis here"
                    },
                    "improvements":[
                        {
                            "area":"Area name",
                            "recommendation":"Detailed recommendation"
                        }
                    ],
                    "suggestion"[
                        {
                            "workout": "Workout name",
                            "description": "Detailed workout description"
                        }
                    ],
                    "safety":[
                        "Safety point 1",
                        "Safety point 2"
                    ]
                }
                
                Analyze this activity:
                Activity Type: %s
                Duration: %d minutes
                Calories Burned : %d
                
                Provide detailed analysis focusing on performance, improvements, next workout suggestions and safety guidelines.
                
                Ensure the response follows the EXACT JSON format shown above.
                """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned()
        );
    }

    private Recommendation processAiResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .get("parts")
                    .get(0)
                    .path("text");
            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n","")
                    .replaceAll("\\n```","")
                    .trim();
            JsonNode analysisJson = objectMapper.readTree(jsonContent);
            JsonNode analysisNode = analysisJson.path("analysis");
            StringBuilder fullAnalysis = new StringBuilder();
            addAnalysisSection(fullAnalysis, analysisNode, "overall", "Overall:");
            addAnalysisSection(fullAnalysis, analysisNode, "pace", "Pace:");
            addAnalysisSection(fullAnalysis, analysisNode, "heartRate", "Heart Rate:");
            addAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "Calories:");

            List<String> improvements = extractImprovements(analysisJson.path("improvements"));
            List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
            List<String> safety = extractSafetyGuidelines(analysisJson.path("safety"));

            return Recommendation.builder()
                    .activityId(activity.getActivityId())
                    .userId(activity.getUserId())
                    .recommendation(fullAnalysis.toString().trim())
                    .improvements(improvements)
                    .suggestion(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            return createDefaultRecommendation(activity);
        }
    }

    private List<String> extractSafetyGuidelines(JsonNode safetyNode) {
        List<String> safety = new ArrayList<>();
        if (safetyNode.isArray()) {
            safetyNode.forEach(item -> safety.add(item.asText()));
        }
        return safety.isEmpty() ?
                Collections.singletonList("Follow general safety guidelines") :
                safety;
    }

    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String> suggestions = new ArrayList<>();
        if (suggestionsNode.isArray()) {
            suggestionsNode.forEach(suggestion -> {
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description").asText();
                suggestions.add(String.format("%s: %s", workout, description));
            });
        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No specific suggestions provided") :
                suggestions;
    }

    private List<String> extractImprovements(JsonNode improvementsNode) {
        List<String> improvements = new ArrayList<>();
        if (improvementsNode.isArray()) {
            improvementsNode.forEach(improvement -> {
                String area = improvement.path("area").asText();
                String detail = improvement.path("recommendation").asText();
                improvements.add(String.format("%s: %s", area, detail));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No specific improvements provided") :
                improvements;
    }

    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if(!analysisNode.path(key).isMissingNode()){
            fullAnalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }

    }

    private Recommendation createDefaultRecommendation(Activity activity) {
        return Recommendation.builder()
                .activityId(activity.getActivityId())
                .userId(activity.getUserId())
                .recommendation("Unable to generate detailed analysis")
                .improvements(Collections.singletonList("Continue with your current routine"))
                .suggestion(Collections.singletonList("Consider consulting a fitness consultant"))
                .safety(Arrays.asList(
                        "Always warm up before exercise",
                        "Stay hydrated",
                        "Listen to your body"
                ))
                .createdAt(LocalDateTime.now())
                .build();
    }

}
