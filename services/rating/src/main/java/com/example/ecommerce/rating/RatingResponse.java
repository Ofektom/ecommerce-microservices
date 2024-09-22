package com.example.ecommerce.rating;

import java.time.LocalDateTime;

public record RatingResponse(
        String id,
        Integer productId,
        String customerId,
        int rating,
        String comment,
        LocalDateTime createdAt
) {
}
