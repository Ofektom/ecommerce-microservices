package com.example.ecommerce.recommender;

import com.example.ecommerce.rating.RatingClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModelTrainerService {

    private final RatingConversionService ratingConversionService;
    private ALSModel alsModel;


    @Scheduled(cron = "0 0 0 * * ?")
    public void trainAndSaveModel() {
        log.info("Starting scheduled model training...");

        // Fetch ratings from the rating service
        Dataset<Row> ratings = ratingConversionService.getRatingsData();

        if (ratings == null || ratings.isEmpty()) {
            log.error("No new ratings available for training.");
            return;
        }

        // Set up ALS algorithm
        ALS als = new ALS()
                .setMaxIter(10)
                .setRegParam(0.1)
                .setUserCol("customerId")
                .setItemCol("productId")
                .setRatingCol("rating");

        // Train the model
        ALSModel model = als.fit(ratings);

        // Define path to save the model
        String modelSavePath = new File("src/main/resources/model").getAbsolutePath();

        try {
            model.save(modelSavePath);
            log.info("Model saved successfully at: {}", modelSavePath);
        } catch (Exception e) {
            log.error("Failed to save the model.", e);
        }

        // Set the trained model for use in recommendations
        this.alsModel = model;
    }

    public void loadModel() {
        String modelLoadPath = new File("src/main/resources/model").getAbsolutePath();
        try {
            this.alsModel = ALSModel.load(modelLoadPath);
            log.info("Model loaded successfully from: {}", modelLoadPath);
        } catch (Exception e) {
            log.error("Failed to load the model.", e);
        }
    }

    public ALSModel getAlsModel() {
        if (alsModel == null) {
            loadModel();
        }
        return alsModel;
    }
}
