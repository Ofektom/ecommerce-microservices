package com.ofektom.ecommerce.payment;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class PaymentClient {

    private final RestTemplate restTemplate;
    @Value("${application.config.payment-url}")
    private String paymentUrl;

    public Integer requestOrderPayment(PaymentRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Integer> response = restTemplate.exchange(paymentUrl, HttpMethod.POST, entity, Integer.class);

        return response.getBody();
    }
}