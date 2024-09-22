package com.example.ecommerce.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/add")
    public ResponseEntity<RatingResponse> addRating(@RequestBody RatingRequest ratingDto) {
        RatingResponse rating = ratingService.addRating(ratingDto);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getRatingsForProduct(@PathVariable Integer productId) {
        List<Rating> ratings = ratingService.getRatingsForProduct(productId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/product/{productId}/average")
    public ResponseEntity<Double> getAverageRatingForProduct(@PathVariable Integer productId) {
        double averageRating = ratingService.getAverageRatingForProduct(productId);
        return ResponseEntity.ok(averageRating);
    }
}
