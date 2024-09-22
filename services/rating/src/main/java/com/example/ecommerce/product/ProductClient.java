package com.example.ecommerce.product;

import com.example.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {
    @Value("${application.config.product-url}")
    private String productUrl;

    private final RestTemplate restTemplate;

    public List<ProductResponse> getAllProducts() {
        ResponseEntity<List<ProductResponse>> response = restTemplate.exchange(
                productUrl + "/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ProductResponse>>() {}
        );
        return response.getBody();
    }

    public Boolean existsByProductId(Integer productId) {
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                productUrl + "/exists/" + productId,
                HttpMethod.GET,
                null,
                Boolean.class
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new BusinessException("An error occurred while checking if product ID exists: " + responseEntity.getStatusCode());
        }

        return responseEntity.getBody();
    }

}

