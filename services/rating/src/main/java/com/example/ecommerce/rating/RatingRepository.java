package com.example.ecommerce.rating;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByProductId(Integer productId);
    List<Rating> findByCustomerId(String customerId);
}
