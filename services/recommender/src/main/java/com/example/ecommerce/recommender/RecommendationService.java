package com.example.ecommerce.recommender;

import lombok.RequiredArgsConstructor;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final ModelTrainerService modelTrainerService;
    private final SparkSession sparkSession;


    public Dataset<Row> recommendProductsForUser(String customerId, int numRecommendations) {
        ALSModel alsModel = modelTrainerService.getAlsModel();

        if (alsModel == null) {
            throw new IllegalStateException("ALS model has not been trained or loaded.");
        }

        Dataset<Row> customers = sparkSession.createDataFrame(
                        java.util.Collections.singletonList(customerId), Integer.class)
                .toDF("customerId");

        return alsModel.recommendForUserSubset(customers, numRecommendations);
    }
}

