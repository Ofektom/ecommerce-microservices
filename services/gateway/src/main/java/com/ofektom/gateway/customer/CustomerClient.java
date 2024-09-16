package com.ofektom.gateway.customer;


import com.ofektom.gateway.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerClient {
    @Value("${application.config.customer-url}")
    private String customerUrl;
    private final RestTemplate restTemplate;

    public void createCustomer(CustomerDetails customerDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<CustomerDetails> requestEntity = new HttpEntity<>(customerDetails, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                customerUrl + "/create",
                HttpMethod.POST,
                requestEntity,
                Void.class
        );

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while creating the customer: " + responseEntity.getStatusCode());
        }
    }

    public Boolean existsByEmail(String email) {
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                customerUrl + "/exists/" + email,
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

