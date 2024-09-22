package com.example.ecommerce.recommender;

import com.example.ecommerce.rating.Rating;
import com.example.ecommerce.rating.RatingClient;
import lombok.AllArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RatingConversionService {

    private final SparkSession sparkSession;
    private final RatingClient ratingClient;

    public Dataset<Row> getRatingsData() {
        // Fetch ratings from RatingClient
        List<Rating> ratings = ratingClient.getAllRatings();

        // Map List<Rating> to List<Row> (with necessary fields for ALS: userId, productId, rating)
        List<Row> rows = ratings.stream()
                .map(rating -> RowFactory.create(rating.getCustomerId(), rating.getProductId(), rating.getRating()))
                .collect(Collectors.toList());

        // Define schema for the DataFrame
        StructType schema = new StructType(new StructField[]{
                DataTypes.createStructField("customerId", DataTypes.StringType, false),
                DataTypes.createStructField("productId", DataTypes.IntegerType, false),
                DataTypes.createStructField("rating", DataTypes.IntegerType, false)
        });

        // Create DataFrame from List<Row> and schema
        return sparkSession.createDataFrame(rows, schema);
    }
}
