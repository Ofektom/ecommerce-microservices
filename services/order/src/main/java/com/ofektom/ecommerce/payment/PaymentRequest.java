package com.ofektom.ecommerce.payment;

import com.ofektom.ecommerce.customer.CustomerResponse;
import com.ofektom.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
