package com.example.ecommerce.customer;

import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerClient {
    @Value("${application.config.customer-url}")
    private String customerUrl;

    private final RestTemplate restTemplate;

    public Boolean existsByCustomerId(String customerId) {
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                customerUrl + "/exists/" + customerId,
                HttpMethod.GET,
                null,
                Boolean.class
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new BusinessException("An error occurred while checking if email exists: " + responseEntity.getStatusCode());
        }

        return responseEntity.getBody();
    }
}
