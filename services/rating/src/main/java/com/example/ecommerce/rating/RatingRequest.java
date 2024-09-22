package com.example.ecommerce.rating;

public record RatingRequest(
        Integer productId,
        String customerId,
        int rating,
        String comment

) {
}
