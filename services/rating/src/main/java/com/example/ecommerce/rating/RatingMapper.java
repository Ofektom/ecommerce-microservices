package com.example.ecommerce.rating;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RatingMapper {
    public Rating toRating(RatingRequest request) {
        return Rating.builder()
                .customerId(request.customerId())
                .productId(request.productId())
                .comment(request.comment())
                .rating(request.rating())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public RatingResponse fromRating(Rating rating) {
        return new RatingResponse(
                rating.getId(),
                rating.getProductId(),
                rating.getCustomerId(),
                rating.getRating(),
                rating.getComment(),
                rating.getCreatedAt()
        );
    }
}
