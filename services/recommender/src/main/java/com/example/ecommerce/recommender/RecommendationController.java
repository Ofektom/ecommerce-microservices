package com.example.ecommerce.recommender;

import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/recommenders")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/recommendations")
    public Dataset<Row> getRecommendations(@RequestParam String customerId, @RequestParam int numRecommendations) {
        return recommendationService.recommendProductsForUser(customerId, numRecommendations);
    }
}

