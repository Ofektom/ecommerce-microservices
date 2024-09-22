package com.example.ecommerce.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingClient {

    @Value("${application.config.rating-url}")
    private String ratingUrl;

    private final RestTemplate restTemplate;

    // Fetch all ratings data
    public List<Rating> getAllRatings() {
        ResponseEntity<List<Rating>> response = restTemplate.exchange(
                ratingUrl + "/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}

