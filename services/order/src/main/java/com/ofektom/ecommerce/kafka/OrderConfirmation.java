package com.ofektom.ecommerce.kafka;

import com.ofektom.ecommerce.customer.CustomerResponse;
import com.ofektom.ecommerce.order.PaymentMethod;
import com.ofektom.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
