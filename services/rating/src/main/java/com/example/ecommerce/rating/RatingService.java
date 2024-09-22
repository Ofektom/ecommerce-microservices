package com.example.ecommerce.rating;

import com.example.ecommerce.customer.CustomerClient;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.product.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper mapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public RatingResponse addRating(RatingRequest request) {
        if(!customerClient.existsByCustomerId(request.customerId())){
            throw new BusinessException(format("Customer with ID %s does not exit", request.customerId()));
        }

        if(!productClient.existsByProductId(request.productId())){
            throw new BusinessException(format("Product with ID %s does not exit", request.productId()));
        }

        var rating = ratingRepository.save(mapper.toRating(request));
        return mapper.fromRating(rating);
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> getRatingsForProduct(Integer productId) {
        return ratingRepository.findByProductId(productId);
    }

    public double getAverageRatingForProduct(Integer productId) {
        List<Rating> ratings = ratingRepository.findByProductId(productId);
        OptionalDouble average = ratings.stream()
                .mapToInt(Rating::getRating)
                .average();
        return average.isPresent() ? average.getAsDouble() : 0;
    }
}
